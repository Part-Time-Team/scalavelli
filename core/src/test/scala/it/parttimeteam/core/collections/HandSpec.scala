package it.parttimeteam.core.collections

import it.parttimeteam.core.TestCards._
import org.scalatest.funsuite.AnyFunSuite

class HandSpec extends AnyFunSuite {

  test("Check add cards to the list playerCard") {
    val hand: Hand = Hand(List(ACE_CLUBS_BLUE))
    assert(hand.addPlayerCards(FOUR_SPADES :: (KING_DIAMONDS :: Nil)) equals Hand(List(ACE_CLUBS_BLUE, FOUR_SPADES, KING_DIAMONDS)))
  }

  test("Check add cards to the list tableCard") {
    val hand: Hand = Hand(List(QUEEN_DIAMONDS), List(ACE_CLUBS_BLUE))
    assert(hand.addBoardCards(FOUR_SPADES :: (KING_DIAMONDS :: Nil)) equals
      Hand(QUEEN_DIAMONDS :: Nil, ACE_CLUBS_BLUE :: (FOUR_SPADES :: (KING_DIAMONDS :: Nil))))
  }

  test("Contains cards that are present") {
    val hand = Hand(List(ACE_CLUBS_BLUE, FOUR_SPADES, KING_DIAMONDS))
    assert(hand containsCards ACE_CLUBS_BLUE)
    assert(hand containsCards(ACE_CLUBS_BLUE, FOUR_SPADES))
    assert(hand containsCards(ACE_CLUBS_BLUE, FOUR_SPADES, KING_DIAMONDS))
  }

  test("Contains cards that are not present") {
    val hand = Hand(List(ACE_CLUBS_BLUE, FOUR_SPADES))
    assert(!(hand containsCards KING_DIAMONDS))
    assert(!(hand containsCards QUEEN_DIAMONDS))
    assert(!(hand containsCards(KING_DIAMONDS, QUEEN_DIAMONDS)))
  }

  test("Remove cards from player cards that are present") {
    val seq = Seq(ACE_CLUBS_BLUE, FOUR_SPADES)
    val hand = Hand(List(ACE_CLUBS_BLUE, FOUR_SPADES, KING_DIAMONDS, QUEEN_DIAMONDS))
    val removed = hand.removeCards(seq)
    assert(removed.isRight)
    assertResult(KING_DIAMONDS +: (QUEEN_DIAMONDS +: Nil))(removed.right.get.playerCards)
  }

  test("Remove cards from board cards that are present") {
    val seq = Seq(ACE_CLUBS_BLUE, FOUR_SPADES)
    val hand = Hand(List.empty, List(ACE_CLUBS_BLUE, FOUR_SPADES, KING_DIAMONDS, QUEEN_DIAMONDS))
    val removed = hand.removeCards(seq)
    assert(removed.isRight)
    assertResult(Seq(KING_DIAMONDS, QUEEN_DIAMONDS))(removed.right.get.boardCards)
  }

  test("Remove cards from hand that are not present") {
    val seq = Seq(ACE_CLUBS_BLUE, FOUR_SPADES)
    val hand = Hand(List(KING_DIAMONDS, QUEEN_DIAMONDS))
    val removed = hand.removeCards(seq)
    assert(removed.isLeft)
  }
}