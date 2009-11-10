package org.comperio.scampp.socket

import actors.Actor._
import actors.Exit
import collection.mutable.ArrayBuffer
import java.io.{ByteArrayOutputStream, ByteArrayInputStream}
import java.net.Socket
import specs.matcher.Matcher
import specs.mock.{JMocker, ClassMocker, Mockito}
import specs.runner.JUnit4
import specs.Specification

/**
 * Created by IntelliJ IDEA.
 * User: nfma
 * Date: Nov 2, 2009
 * Time: 9:09:36 PM
 * To change this template use File | Settings | File Templates.
 */

class SocketConnectionHandlerTest extends JUnit4(SocketConnectionHandlerSpec)
object SocketConnectionHandlerSpec extends Specification with JMocker with ClassMocker {
  val handler = SocketConnectionHandler

  doBeforeSpec(handler.start)
  doAfterSpec(presenceMessageHandler ! "forced shutdown")

  val presenceMessageHandler = actor {
    link(handler)
    react {
      case _ => {
//        println("received stuff")
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
      Thread.sleep(100)
      new String(os.toByteArray) must matchXml(
              <stream:stream xmlns="jabber:client" xmlns:stream='http://etherx.jabber.org/streams' id='c2s_123'
              from='example.com' version='1.0'/>.toString +
              <stream:features>
                <starttls xmlns='urn:ietf:params:xml:ns:xmpp-tls'>
                  <required/>
                </starttls>
                <mechanisms xmlns='urn:ietf:params:xml:ns:xmpp-sasl'>
                  <mechanism>DIGEST-MD5</mechanism>
                  <mechanism>PLAIN</mechanism>
                </mechanisms>
              </stream:features>.toString)
    }
  }

  def matchXml(a: String) = new Matcher[String] {
    class StringWrapper(s: String) {
      def removeBadSpaces = s.replaceAll(">\\s+<", "><").replaceAll("\\s+", " ")
    }

    implicit def stringWrapper(s: String) = new StringWrapper(s)

    def apply(v: => String) = (v.removeBadSpaces == a.removeBadSpaces, "okMessage", "koMessage")
  }
}