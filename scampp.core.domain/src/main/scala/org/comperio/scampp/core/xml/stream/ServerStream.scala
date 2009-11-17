package org.comperio.scampp.core.xml.stream

import org.comperio.scampp.core.xml.ScamppXml

class ServerStream(id: String, from: String) extends Stream(id,from,null) {

  override def equals(p1: Any) = p1.isInstanceOf[ServerStream] &&
                                 p1.asInstanceOf[ServerStream].id == id &&
                                 p1.asInstanceOf[ServerStream].from == from

  // bad implimentation...i'm tired
  override def hashCode = id.hashCode + from.hashCode
}

abstract class Stream(val id: String, val from: String, val to: String) extends ScamppXml {
  val version = "1.0"

  override def toXml = <stream:stream xmlns='jabber:client' xmlns:stream={Stream.nameSpace}
                            version={version}
                            id={id}
                            to={to}
                            from={from} />
}

object Stream {
  val nameSpace = "http://etherx.jabber.org/streams"
}