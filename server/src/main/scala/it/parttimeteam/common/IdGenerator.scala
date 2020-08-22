package it.parttimeteam.common

import java.util.UUID

trait IdGenerator {
  def generateId: String = UUID.randomUUID().toString
}
