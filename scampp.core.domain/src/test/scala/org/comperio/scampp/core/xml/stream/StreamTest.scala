package org.comperio.scampp.core.xml.stream

import org.specs.runner.JUnit4
import org.specs.Specification

class StreamTest extends JUnit4(StreamSpec)
object StreamSpec extends Specification {

  "A Stream " should {
    "have an id and from attribute" in {
      val stream = new Stream("myid", "scampp.com")
      stream.id must_== "myid"
      stream.from must_== "scampp.com"
    }
    "have version 1.0" in {
      new Stream("myid", "scampp.com").version must_== "1.0"
    }
    "be a ScamppXml type and have valid XMPP xml" in {
      val id = "id"
      val from = "scampp.com"
      new Stream(id, from).toXml must_== <stream:stream xmlns="jabber:client"
                                        xmlns:stream="http://etherx.jabber.org/streams"
                                        id={id} from={from} version="1.0"/>
    }
    "be equal to any other Stream with the same id and from attributes" in {
      new Stream("id", "scampp.com") must_== new Stream("id", "scampp.com")
      new Stream("id", "not.scampp.com") must_!= new Stream("id", "scampp.com")
      new Stream("notId", "scampp.com") must_!= new Stream("id", "scampp.com")
    }
  }

}
