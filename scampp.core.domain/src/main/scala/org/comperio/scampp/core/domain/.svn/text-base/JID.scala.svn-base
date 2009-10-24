package org.comperio.scampp.core.domain;

case class JID(jid : String) {
  val MAX_SIZE = 3071
  val size = jid.getBytes().length

  if(size > MAX_SIZE)
    throw new IllegalArgumentException("Maximum acceptabe size is "+MAX_SIZE+" found "+size)



}