package org.comperio.scampp.socket


import actors.Actor._
import actors.{Exit, Actor}
import java.io.{OutputStreamWriter, BufferedReader, InputStreamReader}
import scala.xml.XML

object SocketConnectionHandler extends Actor {
  def act() {
    loop {
      react {
        case SocketConnected(socket) => {
          println("received socketconnected")
          val xml = XML.load(socket.getInputStream)
          val os = socket.getOutputStream
          println(xml)
          xml match {
            case <stream:stream>{body @ _*}</stream:stream> =>
              val writer = new OutputStreamWriter(os)
              XML.write(writer, <response/>, "UTF-8", false, null)
              writer.flush
              writer.close
              println("woot flushed")
            case _ => socket.close
          }
          ""

        }
        case unknown @ _ => println("received wtf unknown: "+unknown) 
      }
    }
  }
}