package it.parttimeteam.`match`

import it.parttimeteam.`match`.TurnManager
import org.scalatest.flatspec.AnyFlatSpecLike

class TurnManagerSpec extends AnyFlatSpecLike {

  private val id1 = "id1"
  private val id2 = "id2"
  private val id3 = "id3"

  private val ids = Seq(id1, id2, id3)

  it should "initialize and return an existing id" in {
    val turnManager = TurnManager(ids)
    assert(ids.contains(turnManager.playerInTurnId))
  }

  it should "return the initial player after x turn changes" in {
    val turnManager = TurnManager(ids)
    val firstPlayer = turnManager.playerInTurnId

    for (i <- ids.indices) {
      turnManager.nextTurn
    }

    assertResult(firstPlayer)(turnManager.playerInTurnId)
  }

}
