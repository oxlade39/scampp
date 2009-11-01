package org.comperio.scampp.socket


import actors.Actor._
import actors.Actor
import java.net.{Socket, ServerSocket}
abstract class ScamppConnector extends Actor{
  val port : Int
  private var socketListeners : List[Actor] = List()

  def act() {
    val serverSocket = new ServerSocket(port)
    loop {
      val socket = serverSocket.accept
      println("Connection established "+socket)
      for(listener <- socketListeners) listener ! SocketConnected(socket)

      receiveWithin(0) {
        case ScamppExit(m) => {
          println("received ScamppExit")
          exit(m)
        }
        case _ => {}
      }
    }
  }

  def addConnectionListener(listener : Actor) {
    socketListeners = listener :: socketListeners
  }

  def listen(body: => Unit) {
    val actor = new Actor {
      def act() = body
    }
    actor.start()
    addConnectionListener(actor)
  }
}

object DefaultScamppConnector extends ScamppConnector {
  val port = 5222
}

abstract class ScamppEvent
case class SocketConnected(socket : Socket) extends ScamppEvent
case class ScamppExit(m : String) extends ScamppEvent