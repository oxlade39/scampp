package org.comperio.scampp.socket


import actors.Actor
import java.io.{BufferedReader, InputStreamReader}
/**
 * Created by IntelliJ IDEA.
 * User: nfma
 * Date: Nov 2, 2009
 * Time: 9:18:22 PM
 * To change this template use File | Settings | File Templates.
 */

object SocketConnectionHandler extends Actor {
  def act() {
    loop {
      react {
        case SocketConnected(socket) => {
          val br = new BufferedReader(new InputStreamReader(socket.getInputStream))

          x match {
            case p: PresenceMessage => presenceMessageHandler ! p
            case _ => sendBadAck(socket.getOutputStream)
          }


          ""
        }
      }
    }
  }
}