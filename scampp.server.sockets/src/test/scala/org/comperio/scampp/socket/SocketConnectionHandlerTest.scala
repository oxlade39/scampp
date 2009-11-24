package org.comperio.scampp.socket

import com.Ostermiller.util.CircularByteBuffer
import java.io._
import scala.actors.Actor._
import scala.xml.{Node, XML, NodeSeq}

import org.specs.matcher.Matcher
import java.net.Socket
import org.specs.mock.{ClassMocker, JMocker}
import org.specs.Specification
import org.specs.runner.JUnit4
import org.comperio.scampp.core.xml.stream.{Features, Stream}

class SocketConnectionHandlerTest extends JUnit4(SocketConnectionHandlerSpec)
object SocketConnectionHandlerSpec extends Specification with JMocker with ClassMocker {
  val handler = SocketConnectionHandler

  doBeforeSpec(handler.start)
  doAfterSpec(presenceMessageHandler ! "forced shutdown")

  val presenceMessageHandler = actor {
    link(handler)
    react {
      case _ => {
        handler ! <bye />
        exit("byebye")
      }
    }
  }

  "A stream message handler" should {
    shareVariables()
    val socket = mock[Socket]
    "respond to a client stream initialisation message and ..." in {
      val cbb = new CircularByteBuffer();
      val clientOS = cbb.getOutputStream
      val serverIS = cbb.getInputStream
      clientOS.write(<stream:stream xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams' to='scampp.com' version='1.0' />.toString.getBytes())
      val serverOS = new ByteArrayOutputStream()
      expect {
        allowing(socket).getInputStream() will returnValue(serverIS)
        allowing(socket).getOutputStream() will returnValue(serverOS)
      }
      handler ! SocketConnected(socket)
      Thread.sleep(500)

      val returnedXml = XML.loadString("<parent>"+new String(serverOS.toByteArray)+"</parent>")
      val stream = returnedXml.child(0)
      val features = returnedXml.child(1)

      stream must equalIgnoreSpace(new Stream("id", "scampp.com").toXml)
      features must equalIgnoreSpace(new Features("DIGEST-MD5" :: "PLAIN" :: Nil).toXml)

      serverOS.reset()
      clientOS.write(<starttls xmlns='urn:ietf:params:xml:ns:xmpp-tls'/>.toString.getBytes())
      clientOS.flush

      Thread.sleep(500)
      val proceed = XML.loadString(new String(serverOS.toByteArray))
      proceed must equalIgnoreSpace(<proceed xmlns='urn:ietf:params:xml:ns:xmpp-tls'/>)
    }
  }
}