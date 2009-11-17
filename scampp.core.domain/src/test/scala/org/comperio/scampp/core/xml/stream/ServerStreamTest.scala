package org.comperio.scampp.core.xml.stream

import org.specs.runner.JUnit4
import org.specs.Specification

class ServerStreamTest extends JUnit4(ServerStreamSpec)
object ServerStreamSpec extends Specification {

  "A ServerStream " should {
    "have an id and from attribute" in {
      val stream = new ServerStream("myid", "scampp.com")
      stream.id must_== "myid"
      stream.from must_== "scampp.com"
    }
    "have version 1.0" in {
      new ServerStream("myid", "scampp.com").version must_== "1.0"
    }
    "be a ScamppXml type and have valid XMPP xml" in {
      val id = "id"
      val from = "scampp.com"
      new ServerStream(id, from).toXml must equalIgnoreSpace(<stream:stream xmlns="jabber:client"
                                        xmlns:stream="http://etherx.jabber.org/streams"
                                        id={id} from={from} version="1.0"/>)
    }
    "be equal to any other ServerStream with the same id and from attributes" in {
      new ServerStream("id", "scampp.com") must_== new ServerStream("id", "scampp.com")
      new ServerStream("id", "not.scampp.com") must_!= new ServerStream("id", "scampp.com")
      new ServerStream("notId", "scampp.com") must_!= new ServerStream("id", "scampp.com")
    }
  }

}
