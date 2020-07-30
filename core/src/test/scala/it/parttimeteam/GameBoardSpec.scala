package it.parttimeteam

import org.scalatest.funsuite.AnyFunSuite

class GameBoardSpec extends AnyFunSuite {

  test("Add combination to game board") {

    val gameBoard: GameBoard = GameBoard(List())

    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Two(), Suit.Clubs())
    val card3: Card = Card(Rank.Three(), Suit.Clubs())

    val cardCombination: CardCombination = CardCombination(List(card1, card2, card3))

    assert(gameBoard.addCombination(cardCombination) equals GameBoard(List(cardCombination)))
  }
}
