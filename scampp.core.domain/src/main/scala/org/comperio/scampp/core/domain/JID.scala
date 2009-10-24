package org.comperio.scampp.core.domain;

object JID {

  def apply(node : String, rest : String) = {
    node + "@" + rest
  }

  def unapply(s: String): Option[(String, String)] = {
    val parts = s split "@"
    if(parts.length == 2) Some(parts(0), parts(1))
    else None
  }


}

object JIDValidator {

  def isJID(s : String) = s match {

    case JID(node, rest) => true
    case _ => false

  }

}