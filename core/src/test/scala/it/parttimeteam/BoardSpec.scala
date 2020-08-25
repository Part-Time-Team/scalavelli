package it.parttimeteam

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.CardCombination
import org.scalatest.funsuite.AnyFunSuite

class BoardSpec extends AnyFunSuite {

  test("Add combination to game board") {
    val gameBoard: Board = Board.empty
    assertResult(Seq.empty)(gameBoard.combinations)

    val c1 = Card.string2card("2♣R")
    val c2 = Card.string2card("3♣B")
    val c3 = Card.string2card("4♣B")

    val cardCombination: CardCombination = CardCombination("#1", Seq(c1, c2, c3))
    assertResult(Board(Seq(cardCombination)))(gameBoard.addCombination(cardCombination))
  }
}
