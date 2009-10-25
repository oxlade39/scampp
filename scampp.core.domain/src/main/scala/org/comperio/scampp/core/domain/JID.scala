package org.comperio.scampp.core.domain

case class JID(n : String, d : String, r : String){
  val node = {
    if(n == null) null
    else n.replace("@", "")
  }

  val domain = d

  val resource = {
    if(r == null) null
    else r.replace("/", "")
  }

  override def toString = {
    node + "@" + domain + "/" + resource
  }

}

object JIDExtractor {
  val NODE_DOMAIN_RESOURCE_REGEXP = """(\w+@)?([\w+\d*\.]+\w+\d*)(/\w+)?""".r
  val MAX_LENGTH = 3071

  def apply(jid: JID) : String = {
    jid.toString
  }

  def unapply(s: String): Option[JID] = {
    s match {
      case NODE_DOMAIN_RESOURCE_REGEXP(node, domain, resource) => Some(new JID(node, domain, resource))
      case _ => None
    }

  }

}

object JIDUtils {

    class JIDStringWrapper(s : String) {
      def isJID = s match {
        case JIDExtractor(JID(node, domain, resource)) => true
        case _ => false
      }

      def notAJID = !isJID
      def isAJID = isJID

    }

    implicit def stringWrapper(s : String) = new JIDStringWrapper(s)

}