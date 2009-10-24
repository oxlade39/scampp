package org.comperio.scampp.core.domain

import apache.commons.lang.RandomStringUtils
import java.lang.String
import junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scalatest.junit.JUnitRunner
import org.junit.Assert.assertTrue
import org.comperio.scampp.core.domain.JID.JIDUtils.stringWrapper

/**
 * see http://www.ietf.org/rfc/rfc3920.txt
 */
@RunWith(classOf[JUnitRunner])
class JIDSpec extends FlatSpec with ShouldMatchers {
  val MAX_LENGTH = 3071

  "A JID" should "have a total maximum length of less than " + MAX_LENGTH + " bytes" in {
    assertTrue(
      "dano@org.comperio/resource" isJID
    )
  }

  it should "not match string with total length greater than " +MAX_LENGTH+ " bytes" in {
    // FIXME this is incorrect, it generates a string of MAX_LENGTH characters not bytes!!!!!
    val badString: String = RandomStringUtils.random(MAX_LENGTH + 1)
    assertTrue(
      badString notAJID
    )
  }
                                          
  it should "be of the following format: [ node '@' ] JIDDomain [ '/' resource ]" in {
    assertTrue(
      "nodeNotAtSomething" notAJID
    )
  }

}

@RunWith(classOf[JUnitRunner])
class JIDDomainSpec extends FlatSpec with ShouldMatchers {

  "A JIDDomain" should "be either a JIDFullyQualifiedDomainName or a JIDAddressLiteral" is (pending)

}

@RunWith(classOf[JUnitRunner])
class JIDAddressLiteralSpec extends FlatSpec with ShouldMatchers {

  "A JIDDomain" should "be an IPv4address or an IPv6address" is (pending)

}