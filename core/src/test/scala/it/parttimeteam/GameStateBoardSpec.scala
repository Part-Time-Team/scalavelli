package it.parttimeteam

import it.parttimeteam.core.cards
import it.parttimeteam.core.cards.{Card, Rank, Suit}
import it.parttimeteam.core.collections.CardCombination
import org.scalatest.funsuite.AnyFunSuite

class GameStateBoardSpec extends AnyFunSuite {

  test("Add combination to game board") {

    val gameBoard: Board = Board(List())

    val card1: Card = cards.Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = cards.Card(Rank.Two(), Suit.Clubs())
    val card3: Card = cards.Card(Rank.Three(), Suit.Clubs())

    val cardCombination: CardCombination = CardCombination(List(card1, card2, card3))

    assert(gameBoard.addCombination(cardCombination) equals Board(List(cardCombination)))
  }
}
