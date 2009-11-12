package org.comperio.scampp.socket

import org.comperio.scampp.core.xml.stream.Stream
import actors.Actor._
import actors.{Exit, Actor}
import java.io.{OutputStreamWriter, BufferedReader, InputStreamReader}
import scala.xml.XML

object SocketConnectionHandler extends Actor {

  val features =
  <stream:features>
     <starttls xmlns='urn:ietf:params:xml:ns:xmpp-tls'>
       <required/>
     </starttls>
     <mechanisms xmlns='urn:ietf:params:xml:ns:xmpp-sasl'>
       <mechanism>DIGEST-MD5</mechanism>
       <mechanism>PLAIN</mechanism>
     </mechanisms>
   </stream:features>; 

  def act() {
    loop {
      react {
        case SocketConnected(socket) => {
          val xml = XML.load(socket.getInputStream)
          val os = socket.getOutputStream
          xml match {
            case <stream:stream>{body @ _*}</stream:stream> =>
              val writer = new OutputStreamWriter(os)
              XML.write(writer, new Stream("id", "scampp.com").toXml, "UTF-8", false, null)
              XML.write(writer, features, "UTF-8", false, null)
              writer.flush
              writer.close
            case _ => socket.close
          }
          ""

        }
        case unknown @ _ => println("received wtf unknown: "+unknown) 
      }
    }
  }
}