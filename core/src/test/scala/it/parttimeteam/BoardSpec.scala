package it.parttimeteam

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.CardCombination
import org.scalatest.funsuite.AnyFunSuite

class BoardSpec extends AnyFunSuite {

  test("Game Board created with 0 combinations at start") {
    val gameBoard = Board.empty
    assertResult(Seq.empty)(gameBoard.combinations)
  }

  test("Add combination to game board") {
    val gameBoard = Board.empty
    val c1 = Card.string2card("2♣R")
    val c2 = Card.string2card("3♣B")
    val c3 = Card.string2card("4♣B")

    val cardCombination: CardCombination = CardCombination("#1", Seq(c1, c2, c3))
    assertResult(Board(Seq(cardCombination)))(gameBoard.addCombination(cardCombination))
  }

  test("Pick a combination from empty board") {
    val gameBoard = Board.empty
    val c1 = Card.string2card("2♣R")
    val res = gameBoard.pickCards(c1 +: Nil)
    assert(res.isLeft)
    assert(res.left.get == "No cards in the board")
  }
}
