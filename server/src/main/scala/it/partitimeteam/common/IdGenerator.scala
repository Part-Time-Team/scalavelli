package it.partitimeteam.common

import java.util.UUID

trait IdGenerator {
  def generateId: String = UUID.randomUUID().toString
}
