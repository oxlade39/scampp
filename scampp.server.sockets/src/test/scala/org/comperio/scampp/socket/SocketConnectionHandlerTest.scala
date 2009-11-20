package org.comperio.scampp.socket

import scala.actors.Actor._
import scala.xml.{Node, XML, NodeSeq}

import org.specs.matcher.Matcher
import java.io.{ByteArrayOutputStream, ByteArrayInputStream}
import java.net.Socket
import org.specs.mock.{ClassMocker, JMocker}
import org.specs.Specification
import org.specs.runner.JUnit4
import org.comperio.scampp.core.xml.stream.{Features, ServerStream}

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
      val is = new ByteArrayInputStream(<stream:stream
        xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams'
                 to='scampp.com' version='1.0' />.toString.getBytes())
      val os = new ByteArrayOutputStream()
      expect {
        one(socket).getInputStream() will returnValue(is)
        one(socket).getOutputStream() will returnValue(os)
      }
      handler ! SocketConnected(socket)
      Thread.sleep(1000)

      val serverResponse: String = new String(os.toByteArray)
      val returnedXml = XML.loadString("<parent>"+serverResponse+"</parent>")
      val stream = returnedXml.child(0)
      val features = returnedXml.child(1)

      stream must equalIgnoreSpace(new ServerStream("id", "scampp.com").toXml)
      features must equalIgnoreSpace(new Features("DIGEST-MD5" :: "PLAIN" :: Nil).toXml)
    }
  }
}