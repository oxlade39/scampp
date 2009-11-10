package org.comperio.scampp.socket


import actors.Actor._
import actors.{Exit, Actor}
import java.io.{OutputStreamWriter, BufferedReader, InputStreamReader}
import scala.xml.XML

object SocketConnectionHandler extends Actor {

  val response = <stream:stream
       xmlns='jabber:client'
       xmlns:stream='http://etherx.jabber.org/streams'
       id='c2s_123'
       from='example.com'
       version='1.0' />
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
              XML.write(writer, response, "UTF-8", false, null)
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