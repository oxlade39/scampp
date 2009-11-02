package org.comperio.scampp.socket

import actors.Actor._
import actors.{Actor}
import java.net.{ConnectException, Socket}
import org.specs._
import org.specs.runner._
import org.specs.matcher._
import org.junit.Assert._


class ScamppConnectorTest extends JUnit4(ScamppConnectorSpec)
object ScamppConnectorSpec extends Specification {

  var scamppConnector = DefaultScamppConnector
  doBeforeSpec { scamppConnector.start }
  doAfterSpec { scamppConnector ! ScamppExit("shutdown") }

  "A DefaultScamppConnector" should {
    "accept connections on port 5222" in {
      val socket = new Socket("localhost", 5222)
      socket.isConnected() must be (true)
    }

    "publish a SocketConnected event when a new connection is established" in {
      var notified = false

      scamppConnector.addConnectionListener {
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

    "exit cleanly when shutdown is called" in {
      scamppConnector ! ScamppExit("shutdown")
      Thread.sleep(100L)
      new Socket("localhost", 5222) must throwA[ConnectException]
    }
  }
}