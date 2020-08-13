package it.parttimeteam

import it.parttimeteam.core.player
import it.parttimeteam.core.player.Player
import org.scalatest.funsuite.AnyFunSuite

class GameSpec extends AnyFunSuite {

  test("Get next Player") {

    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Four(), Suit.Spades())
    val card3: Card = Card(Rank.King(), Suit.Diamonds())
    val card4: Card = Card(Rank.Queen(), Suit.Diamonds())

    val player1 = Player.FullPlayer("Lorenzo", "01", Hand(List(card1)))
    val player2 = Player.FullPlayer("Matteo", "02", Hand(List(card2)))
    val player3 = Player.FullPlayer("Daniele", "03", Hand(List(card3)))
    val player4 = Player.FullPlayer("Luca", "04", Hand(List(card4)))

    val gameBoard : GameBoard = GameBoard(List())
    val game : Game = Game(List(player1, player2, player3, player4), null, 3,gameBoard)

    assert(game.getNextPlayer equals player1)
  }

}
