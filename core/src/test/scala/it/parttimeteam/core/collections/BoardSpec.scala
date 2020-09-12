package it.parttimeteam.core.collections

import it.parttimeteam.core.GameError
import it.parttimeteam.core.cards.Card
import org.scalatest.funsuite.AnyFunSuite

class BoardSpec extends AnyFunSuite {
  var board: Board = Board.empty
  val TWO_CLUBS: Card = "2CR"
  val THREE_CLUBS: Card = "3CB"
  val FOUR_CLUBS: Card = "4CB"
  val FOUR_DIAMONDS: Card = "4DB"
  val FOUR_SPADES: Card = "4SB"
  val comb1: CardCombination = CardCombination("#1", Seq(TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS))
  var comb2: CardCombination = CardCombination("#2", Seq(FOUR_DIAMONDS, FOUR_SPADES))

  test("Game Board created with 0 combinations at start") {
    assertResult(Seq.empty)(board.combinations)
  }

  test("Pick a combination from empty board") {
    val res = board.pickCards(TWO_CLUBS +: Nil)
    assert(res.isLeft)
    assert(res.left.get == GameError.NoCardInBoard)
  }

  test("Add combination to game board") {
    board = board.putCombination(comb1)
    assertResult(Board(Seq(comb1)))(board)
  }

  test("Add a sequence of cards to gameboard") {
    board = board.putCombination(comb2.cards)
    comb2 = board.combinations.last
    assertResult(Board(Seq(comb1, comb2)))(board)
  }

  test("Pick a combination from non empty board") {
    val res = board.pickCards(TWO_CLUBS +: Nil)
    assert(res.isRight)
    board = res.right.get
    val comb = CardCombination("#1", Seq(THREE_CLUBS, FOUR_CLUBS))
    assertResult(Board(Seq(comb, comb2)))(board)
  }

  test("Put cards in a combination on the board") {
    val comb = CardCombination("#1", Seq(TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS))
    board = board.putCards("#1", Seq(TWO_CLUBS))
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
