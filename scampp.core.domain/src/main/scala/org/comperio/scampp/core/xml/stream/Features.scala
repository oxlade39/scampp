package org.comperio.scampp.core.xml.stream

import org.comperio.scampp.core.xml.ScamppXml
import scala.xml.{Comment, Text, NodeSeq, Node}

class Features(val mechanisms: List[String]) extends ScamppXml {
  val tlsRequired = true

  val tlsRequiredNode: Node = if(tlsRequired) <required /> else Comment("please compiler")
  val mechanismNodes: NodeSeq = for(mechanism <- mechanisms) yield <mechanism>{mechanism}</mechanism>

  def toXml = <stream:features>
                <starttls xmlns={Features.tlsNamespace}>{tlsRequiredNode}</starttls>
                <mechanisms xmlns={Features.mechanismsNamespace}>{mechanismNodes}</mechanisms>
              </stream:features>
}

object Features {
  val tlsNamespace = "urn:ietf:params:xml:ns:xmpp-tls"
  val mechanismsNamespace = "urn:ietf:params:xml:ns:xmpp-sasl"
}