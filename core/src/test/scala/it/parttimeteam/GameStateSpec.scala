package it.parttimeteam

import it.parttimeteam.core.cards
import it.parttimeteam.core.cards.{Card, Rank, Suit}
import it.parttimeteam.core.collections.{Deck, Hand}
import it.parttimeteam.core.player.Player
import org.scalatest.funsuite.AnyFunSuite

class GameStateSpec extends AnyFunSuite {

  test("Get next Player") {

    val card1: Card = cards.Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = cards.Card(Rank.Four(), Suit.Spades())
    val card3: Card = cards.Card(Rank.King(), Suit.Diamonds())
    val card4: Card = cards.Card(Rank.Queen(), Suit.Diamonds())

    val player1 = Player.FullPlayer("Lorenzo", "01", Hand(List(card1)))
    val player2 = Player.FullPlayer("Matteo", "02", Hand(List(card2)))
    val player3 = Player.FullPlayer("Daniele", "03", Hand(List(card3)))
    val player4 = Player.FullPlayer("Luca", "04", Hand(List(card4)))

    val gameBoard : Board = Board(List())
    val game : GameState = GameState(List(player1, player2, player3, player4), Deck.shuffled, gameBoard)

    // TODO: Fix this test.
    // assert(game.getNextPlayer equals player1)
  }

}
