package org.comperio.scampp.socket

import actors.Actor._
import actors.Actor
import core.xml.stream.{Features, Stream}
import java.io.OutputStreamWriter
import scala.Nil
import scala.xml.XML
import org.comperio.scampp.core.xml.stream.{ServerStream, Features}

object SocketConnectionHandler extends Actor {
  val supportedMechanisms = "DIGEST-MD5" :: "PLAIN" :: Nil
  val steps =  <stream:stream /> :: <starttls /> :: Nil

  def act() {
    loop {
      react {
        case SocketConnected(socket) => {
          var currentSocket = socket
          for (currentNode <- steps) {
            val array: Array[Byte] = new Array[Byte](1024)
            socket.getInputStream.read(array)
            val xml = XML.loadString(new String(array).trim)
            xml match {
              case <stream:stream>{body@_*}</stream:stream> =>
                val os = socket.getOutputStream
                val writer = new OutputStreamWriter(os)
                XML.write(writer, new ServerStream("id", "scampp.com").toXml, "UTF-8", false, null)
                XML.write(writer, new Features(supportedMechanisms).toXml, "UTF-8", false, null)
                writer.flush
                writer.close
              case <starttls>{body@_*}</starttls> =>
                val os = socket.getOutputStream
                val writer = new OutputStreamWriter(os)
                XML.write(writer, <proceed xmlns='urn:ietf:params:xml:ns:xmpp-tls'/>, "UTF-8", false, null)
                writer.flush
                writer.close
              case _ =>
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