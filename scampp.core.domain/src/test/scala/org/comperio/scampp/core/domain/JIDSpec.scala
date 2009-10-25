package org.comperio.scampp.core.domain

import apache.commons.lang.RandomStringUtils
import java.lang.String
import junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scalatest.junit.JUnitRunner
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.comperio.scampp.core.domain.JIDUtils.stringWrapper

/**
 * see http://www.ietf.org/rfc/rfc3920.txt
 */
@RunWith(classOf[JUnitRunner])
class JIDSpec extends FlatSpec with ShouldMatchers {
  val MAX_LENGTH = 3071
  val JID_FORMAT = "[ node '@' ] JIDDomain [ '/' resource ]"

  "A JID" should "have a total maximum length of less than " + MAX_LENGTH + " bytes" in {
    assertTrue(
      "dano@org.comperio/resource" isAJID
    )
  }

  it should "not match string with total length greater than " +MAX_LENGTH+ " bytes" in {
    // FIXME this is incorrect, it generates a string of MAX_LENGTH characters not bytes!!!!!
    val badString = RandomStringUtils.random(MAX_LENGTH + 1)
    assertFalse(
      badString isAJID
    )
  }

  it should "always have a domain section" in {
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
                                          
  it should "have an  optional node and '@' pair in the following format: "+JID_FORMAT+"" in {
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

  it should "have an  optional resource and '/' pair in the following format: "+JID_FORMAT+"" in {
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