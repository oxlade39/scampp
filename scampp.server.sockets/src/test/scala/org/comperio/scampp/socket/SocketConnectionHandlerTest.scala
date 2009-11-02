package org.comperio.scampp.socket

import actors.Actor._
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

   "A presence message handler" should {
     "read the message from the socket" in {
        val socket = mock[Socket]
        val presenceMessageHandler = actor {
           react {
             case _ => {
              handler ! "<response />"
             }
           }
        }
        val is = new ByteArrayInputStream("<presence message=\"foo\" />\n".getBytes())
        val os = new ByteArrayOutputStream()
        expect {
          one(socket).getInputStream() will returnValue(is)
          one(socket).getOutputStream() will returnValue(os)
        }
        handler ! SocketConnected(socket)
        new String(os.toByteArray) must be ("<response />")
     }
   }
}