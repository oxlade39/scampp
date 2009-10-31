package org.comperio.scampp.socket


import actors.Actor
import actors.Actor._
import java.net.ServerSocket

abstract class ScamppConnector extends Actor{

  val port : Int

  def act() {
    val serverSocket = new ServerSocket(port)
    loop {
      serverSocket.accept

      react {
        case Shutdown => exit("shutdown")
      }
    }
  }

}

object DefaultScamppConnector extends ScamppConnector {
  val port = 5222
}

case class Shutdown