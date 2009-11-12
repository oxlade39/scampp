package org.comperio.scampp.core.xml.stream

import org.comperio.scampp.core.xml.ScamppXml

class Stream(val id: String, val from: String) extends ScamppXml {
  val version = "1.0"
  override def toXml = <stream:stream xmlns='jabber:client' xmlns:stream={Stream.nameSpace}
                             version={version} id={id} from={from} />


  override def equals(p1: Any) = p1.isInstanceOf[Stream] &&
                                 p1.asInstanceOf[Stream].id == id &&
                                 p1.asInstanceOf[Stream].from == from

  // bad implimentation...i'm tired
  override def hashCode = id.hashCode + from.hashCode
}

object Stream {
  val nameSpace = "http://etherx.jabber.org/streams"
}