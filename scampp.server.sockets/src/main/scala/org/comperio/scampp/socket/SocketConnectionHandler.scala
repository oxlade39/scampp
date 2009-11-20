package org.comperio.scampp.socket

import actors.Actor._
import actors.{Exit, Actor}
import core.xml.stream.{Features, ServerStream}
import java.io.{OutputStreamWriter}
import scala.xml.XML

object SocketConnectionHandler extends Actor {

  val supportedMechanisms = "DIGEST-MD5" :: "PLAIN" :: Nil

  def act() {
    loop {
      react {
        case SocketConnected(socket) => {
          val xml = XML.load(socket.getInputStream)
          val os = socket.getOutputStream
          xml match {
            case <stream:stream>{body @ _*}</stream:stream> =>
              val writer = new OutputStreamWriter(os)
              XML.write(writer, new ServerStream("id", "scampp.com").toXml, "UTF-8", false, null)
              XML.write(writer, new Features(supportedMechanisms).toXml, "UTF-8", false, null)
              writer.flush
              writer.close
              "nothing to return"
            case _ => {
              println("not matched")
              socket.close
            }
          }
          ""

        }
        case unknown @ _ => println("received wtf unknown: "+unknown) 
      }
    }
  }
}