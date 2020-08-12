package it.parttimeteam

import it.parttimeteam.core.player.ScalavelliPlayer
import org.scalatest.funsuite.AnyFunSuite

class PlayerSpec extends AnyFunSuite{

  test("Name of the player") {
    val player : ScalavelliPlayer = core.player.ScalavelliPlayer("Lorenzo", "#001", Hand(List(), List()))
    assert(player.getName equals "Player name: Lorenzo")
  }

  test("Id of the player") {
    val player : ScalavelliPlayer = core.player.ScalavelliPlayer("Lorenzo", "#001", Hand(List(), List()))
    assert(player.getId equals "Player id: #001")
  }

  test("Hand of the player") {
    val player : ScalavelliPlayer = core.player.ScalavelliPlayer("Lorenzo", "#001", Hand(List(), List()))
    assert(player.hand equals Hand(List(), List()))
  }

  test("Update Hand of the player") {
    val card1 : Card = Card(Rank.Eight(), Suit.Clubs())
    val card2 : Card = Card(Rank.Seven(), Suit.Spades())
    val player : ScalavelliPlayer = core.player.ScalavelliPlayer("Lorenzo", "#d502", Hand(List(), List()))

    assert(player refreshHand(List(card1), List(card2)) equals Hand(List(card1), List(card2)))
  }
}
