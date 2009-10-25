package org.comperio.scampp.core.domain

case class JID(n : Option[String], d : String, r : Option[String]){

  val node = n.map { s => s.replace("@", "") }

  val domain = d

  val resource = r.map { s => s.replace("/", "") }

  override def toString = {
    n.getOrElse("") + domain + r.getOrElse("")
  }

}

object JIDExtractor {
  val NODE_DOMAIN_RESOURCE_REGEXP = """(\w+@)?([\w+\d*\.]+\w+\d*)(/\w+)?""".r
  val MAX_LENGTH = 3071

  def apply(jid: Option[JID]) : String = {
    jid.getOrElse("").toString
  }

  def unapply(s: String): Option[JID] = {
    s match {
      case NODE_DOMAIN_RESOURCE_REGEXP(null, domain, null) => Some(new JID(None, domain, None))
      case NODE_DOMAIN_RESOURCE_REGEXP(null, domain, resource) => Some(new JID(None, domain, Some(resource)))
      case NODE_DOMAIN_RESOURCE_REGEXP(node, domain, null) => Some(new JID(Some(node), domain, None))
      case NODE_DOMAIN_RESOURCE_REGEXP(node, domain, resource) => Some(new JID(Some(node), domain, Some(resource)))
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