package org.comperio.scampp.socket

import actors.Actor._
import actors.Exit
import java.io.{ByteArrayOutputStream, ByteArrayInputStream}
import java.net.Socket
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
    "read the message from the socket" in {
      val socket = mock[Socket]
      val is = new ByteArrayInputStream("""<stream:stream
        xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams'
                 to='example.com' version='1.0' />""".getBytes())
      val os = new ByteArrayOutputStream()
      expect {
        one(socket).getInputStream() will returnValue(is)
        one(socket).getOutputStream() will returnValue(os)
      }
      handler ! SocketConnected(socket)
      Thread.sleep(1000)
      new String(os.toByteArray) must_== <response/>.toString
    }
  }
}