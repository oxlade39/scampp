package org.comperio.scampp.socket


import java.io.{ByteArrayInputStream, InputStream}
import javax.net.SocketFactory
import javax.net.ssl.SSLSocketFactory
import scala.actors.Actor._
import java.net.{Socket, ServerSocket}
import specs.mock.{JMocker, ClassMocker}
import specs.runner.JUnit4
import specs.Specification

/**
 * Created by IntelliJ IDEA.
 * User: nfma
 * Date: Dec 1, 2009
 * Time: 8:28:12 PM
 * To change this template use File | Settings | File Templates.
 */

class SocketSecurerTest extends JUnit4(SocketSecurerSpec)
object SocketSecurerSpec extends Specification with JMocker with ClassMocker {

  "A socket securer" should {
    "upgrade the socket to a secure socket" in {
//      val server = new ServerSocket(44700)
//      val socketFactory = SSLSocketFactory.getDefault
//      actor {
//        val serverPlainSocket = server.accept
//        Thread.sleep(1000)
//        val sslSocket: Socket = socketFactory.asInstanceOf[SSLSocketFactory].createSocket(serverPlainSocket, "localhost", 44700, true)
//        val inputStream = sslSocket.getInputStream
//        val array = new Array[Byte](1024)
//        inputStream.read(array)
//        println(new String(array))
//        val outputStream = serverPlainSocket.getOutputStream
//        outputStream.write("nice stuff".getBytes)
//      }
//      val socket = new Socket("localhost", 44700)
//      val sslSocket: Socket = socketFactory.asInstanceOf[SSLSocketFactory].createSocket(socket, "localhost", 44700, true)
//      println("got here?")
//      val outputStream = sslSocket.getOutputStream()
//      println("got here?")
//      outputStream.write("something".getBytes());
//      outputStream.flush
//      outputStream.close
//      Thread.sleep(1500)
//      val inputStream = sslSocket.getInputStream
//      val array = new Array[Byte](1024)
//      println(new String(array))
//      println("got here?")
//      server.close
    }
  }
}
