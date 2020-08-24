package it.parttimeteam.core.collections

import it.parttimeteam.core.cards
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import org.scalatest.funsuite.AnyFunSuite

class HandSpec extends AnyFunSuite {
  val c1: Card = cards.Card(Rank.Ace(), Suit.Clubs(), Color.Red())
  val c2: Card = cards.Card(Rank.Four(), Suit.Spades(), Color.Red())
  val c3: Card = cards.Card(Rank.King(), Suit.Diamonds(), Color.Blue())
  val c4: Card = cards.Card(Rank.Queen(), Suit.Diamonds(), Color.Blue())

  test("Check add cards to the list playerCard") {
    val hand: Hand = Hand(List(c1))
    assert(hand.addPlayerCards(c2 :: (c3 :: Nil)) equals Hand(List(c1, c2, c3)))
  }

  test("Check add cards to the list tableCard") {
    val hand: Hand = Hand(List(c4), List(c1))
    assert(hand.addTableCards(c2 :: (c3 :: Nil)) equals
      Hand(c4 :: Nil, c1 :: (c2 :: (c3 :: Nil))))
  }

  test("Get player's hand") {
    val hand: Hand = Hand(List(c1, c2), List())
    assert(hand.getHand equals Hand(List(c1, c2), List()))
  }

  test("Sort cards by value") {
    val hand: Hand = Hand(List(), List())
    assert(hand.sortByValue(c3, c4, c1, c2) equals List(c1, c2, c4, c3))
  }

  test("Sort cards by suit") {
    val hand: Hand = Hand(List(), List())
    assert(hand.sortBySuit(c1, c2, c3, c4) equals List(c3, c4, c1, c2))
  }

  test("Contains cards that are present") {
    val hand = Hand(List(c1, c2, c3))
    assert(hand containsCards c1)
    assert(hand containsCards(c1, c2))
    assert(hand containsCards(c1, c2, c3))
  }

  test("Contains cards that are not present") {
    val hand = Hand(List(c1, c2))
    assert(!(hand containsCards c3))
    assert(!(hand containsCards c4))
    assert(!(hand containsCards (c3, c4)))
  }

  test("Remove cards from hand that are present") {
    val seq = Seq(c1, c2)
    val hand = Hand(List(c1, c2, c3, c4))
    val removed = hand.removeCards(seq)
    println(removed)
    assert(removed.isRight)
    assertResult(c1 +: (c2 +: Nil))(removed.right.get._2)
    assertResult(c3 +: (c4 +: Nil))(removed.right.get._1.playerCards)
  }

  test("Remove cards from hand that are not present") {
    val seq = Seq(c1, c2)
    val hand = Hand(List(c3, c4))
    val removed = hand.removeCards(seq)
    assert(removed.isLeft)
  }
}