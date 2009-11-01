package org.comperio.scampp.socket

import actors.Actor._
import actors.{Actor}
import java.net.Socket
import org.specs._
import org.specs.runner._
import org.junit.Assert._


class ScamppConnectorTest extends JUnit4(ScamppConnectorSpec)
object ScamppConnectorSpec extends Specification {

  var scamppConnector = DefaultScamppConnector
  doBeforeSpec { scamppConnector.start }
  doAfterSpec { sendDefaultKill }

  "A DefaultScamppConnector" should {
    "accept connections on port 5222" in {
      val socket = new Socket("localhost", 5222)
      val out = socket.getOutputStream()
      out.flush
      out.close
      socket.isConnected() must be (true)
    }
    "publish a SocketConnected event when a new connection is established" in {
      var notified = false

      scamppConnector.listen {
        react {
          case SocketConnected(socket) => {
            notified = true
            socket.close
            exit
          }
          case _ => exit
        }
      }

      val socket = new Socket("localhost", 5222)
      Thread.sleep(50L)
      notified must be (true)
    }
  }

  def sendDefaultKill() {
    scamppConnector ! ScamppExit("test exit")
    new Socket("localhost", 5222).close
  }

}

