package org.comperio.scampp.socket

import java.io.{OutputStream}
import java.net.Socket
import org.specs._
import org.specs.runner._
import org.junit.Assert._


class ScamppConnectorTest extends JUnit4(ScamppConnectorSpec)
object ScamppConnectorSpec extends Specification {

  val scamppConnector = DefaultScamppConnector
  doBeforeSpec { scamppConnector.start }
  doAfterSpec { scamppConnector ! Shutdown }

  "A DefaultScamppConnector" should {
    "accept connections on port 5222" in {
      val socket = new Socket("localhost", 5222)
      val out = socket.getOutputStream()
      out.flush
      out.close
    }
  }

}