package it.parttimeteam.core.collections

import it.parttimeteam.core.cards.Card
import org.scalatest.funsuite.AnyFunSuite

class BoardSpec extends AnyFunSuite {
  var board: Board = Board.empty
  val c1: Card = "2♣R"
  val c2: Card = "3♣B"
  val c3: Card = "4♣B"
  val c4: Card = "4♦B"
  val c5: Card = "4♠B"
  val comb2: CardCombination = CardCombination("#2", Seq(c4, c5))

  test("Game Board created with 0 combinations at start") {
    assertResult(Seq.empty)(board.combinations)
  }

  test("Pick a combination from empty board") {
    val res = board.pickCards(c1 +: Nil)
    assert(res.isLeft)
    assert(res.left.get == "No cards in the board")
  }

  test("Add combination to game board") {
    val comb = CardCombination("#1", Seq(c1, c2, c3))
    board = board.putCombination(comb, comb2)
    assertResult(Board(Seq(comb, comb2)))(board)
  }

  test("Pick a combination from non empty board") {
    val res = board.pickCards(c1 +: Nil)
    assert(res.isRight)
    board = res.right.get
    val comb = CardCombination("#1", Seq(c2, c3))
    assertResult(Board(Seq(comb, comb2)))(board)
  }

  test("Put cards in a combination on the board") {
    val comb = CardCombination("#1", Seq(c1, c2, c3))
    board = board.putCards("#1", Seq(c1))
    assertResult(Board(Seq(comb, comb2)))(board)
  }

  test("Pick cards from a combination and leave it empty") {
    val comb = board.combinations.head
    val tail = board.combinations.tail
    val res = board.pickCards(comb.cards)
    assert(res.isRight)
    assertResult(tail)(res.right.get.combinations)
  }
}
