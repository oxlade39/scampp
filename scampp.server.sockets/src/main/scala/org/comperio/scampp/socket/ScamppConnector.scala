package org.comperio.scampp.socket


import actors.Actor._
import actors.Actor
import java.net.{Socket, ServerSocket}

abstract class ScamppConnector extends Actor{
  val port : Int
  private var socketListeners : List[Actor] = List()

  def act() {
    val serverSocket = new ServerSocket(port)

    val connectionAccepter = actor {
        loop {
          val socket = serverSocket.accept
          println("Connection established " + socket)
          for (listener <- socketListeners) listener ! SocketConnected(socket)
        }
      }

    link(connectionAccepter)

    receive {
      case ScamppExit(m) => {
        println("received ScamppExit")
        serverSocket.close
        exit(m)
      }

      case _ => {}
    }
  }

  private def addConnectionListener(listener : Actor) {
    socketListeners = listener :: socketListeners
  }

  def addConnectionListener(body: => Unit) {
    val listenerActor = actor { body }
    addConnectionListener(listenerActor)
  }

}

object DefaultScamppConnector extends ScamppConnector {
  val port = 5222
}

abstract class ScamppEvent
case class SocketConnected(socket : Socket) extends ScamppEvent
case class ScamppExit(m : String) extends ScamppEvent