package org.comperio.scampp.core.domain

import org.apache.commons.lang.RandomStringUtils
import java.lang.String
import junit.runner.RunWith
import org.specs.matcher.xUnit
//import org.scalatest.junit.JUnitRunner
import org.comperio.scampp.core.domain.JIDUtils.stringWrapper
import org.specs._
import org.specs.runner._

/**
 * see http://www.ietf.org/rfc/rfc3920.txt
 */
class jidTest extends JUnit4(JIDSpec)
object JIDSpec extends Specification with xUnit{
  val MAX_LENGTH = 3071
  val JID_FORMAT = "[ node '@' ] JIDDomain [ '/' resource ]"

  "A JID" should {
    setSequential()
    "A JID should have a total maximum length of less than " + MAX_LENGTH + " bytes" in {
      assertTrue(
        "dano@org.comperio/resource" isAJID
        )
    }
    "should not match string with total length greater than " + MAX_LENGTH + " bytes" in {
      // FIXME this is incorrect, it generates a string of MAX_LENGTH characters not bytes!!!!!
      val badString = RandomStringUtils.random(MAX_LENGTH + 1)
      assertFalse(
        badString isAJID
        )
    }
    "should always have a domain section" in {
      assertTrue(
        "ietf.org" isAJID
        )
      assertTrue(
        "org" isAJID
        )
      assertTrue(
        "www.ietf.org" isAJID
        )
      assertTrue(
        "dan@" notAJID
        )
      assertTrue(
        "/dan" notAJID
        )
    }
    "should have an  optional node and '@' pair in the following format: " + JID_FORMAT + "" in {
      assertTrue(
        "dan@host.local" isAJID
        )
      assertTrue(
        "host.local" isAJID
        )
      assertTrue(
        "@host.local" notAJID
        )
    }
    "should have an  optional resource and '/' pair in the following format: " + JID_FORMAT + "" in {
      assertTrue(
        "host.local/printer" isAJID
        )
      assertTrue(
        "host.local" isAJID
        )
      assertTrue(
        "host.local/" notAJID
        )
    }
  }
}