package org.comperio.scampp.core.xml.stream

import org.specs.runner.JUnit4
import org.specs.Specification

class FeaturesTest extends JUnit4(FeaturesSpec)
object FeaturesSpec extends Specification {
  "A Features" should {
    "have tlsRequired" in {
      val features = new Features("PLAIN" :: Nil)
      features.tlsRequired must be(true)
    }
    "serialise to xml" in {
      val xml = new Features("DIGEST-MD5" :: "PLAIN" :: Nil).toXml
      xml must equalIgnoreSpace(<stream:features>
                    <starttls xmlns="urn:ietf:params:xml:ns:xmpp-tls">
                      <required />
                    </starttls>
                    <mechanisms xmlns="urn:ietf:params:xml:ns:xmpp-sasl">
                      <mechanism>DIGEST-MD5</mechanism>
                      <mechanism>PLAIN</mechanism>
                    </mechanisms>
                  </stream:features>)
    }
    "be equal to any other Features with the same mechanisms" in {
      val features = new Features("mech1" :: "mech2" :: Nil)
      features must_==  new Features("mech1" :: "mech2" :: Nil)
      features must_!=  new Features("mech2" :: "mech1" :: Nil)
      features must_!=  new Features("mech2" :: Nil)
      features must_!=  new Features("mech1" :: Nil)
      features must_!=  new Features(Nil)
      features must_!=  new Features("mech1" :: "mech2" :: "mech3" :: Nil)
    }
  }
}