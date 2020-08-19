package it.parttimeteam.core.collections

import it.parttimeteam.core.cards
import it.parttimeteam.core.cards.{Card, Rank, Suit}
import org.scalatest.funsuite.AnyFunSuite

class HandSpec extends AnyFunSuite {

  test("Check add cards to the list playerCard") {
    val card1: Card = cards.Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = cards.Card(Rank.Four(), Suit.Spades())
    val card3: Card = cards.Card(Rank.King(), Suit.Diamonds())

    val hand: Hand = Hand(List(card1))
    assert(hand.addPlayerCards(card2 :: (card3 :: Nil)) equals Hand(List(card1, card2, card3)))
  }

  test("Check add cards to the list tableCard") {
    val card1: Card = cards.Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = cards.Card(Rank.Four(), Suit.Spades())
    val card3: Card = cards.Card(Rank.King(), Suit.Diamonds())
    val card4: Card = cards.Card(Rank.Queen(), Suit.Diamonds())

    val hand: Hand = Hand(List(card4), List(card1))
    assert(hand.addTableCards(card2 :: (card3 :: Nil)) equals
      Hand(card4 :: Nil, card1 :: (card2 :: (card3 :: Nil))))
  }

  test("Get player's hand") {
    val card1: Card = cards.Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = cards.Card(Rank.Four(), Suit.Spades())

    val hand: Hand = Hand(List(card1, card2), List())
    assert(hand.getHand equals Hand(List(card1, card2), List()))
  }

  test("Sort cards by value") {
    val card1: Card = cards.Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = cards.Card(Rank.Four(), Suit.Spades())
    val card3: Card = cards.Card(Rank.King(), Suit.Diamonds())
    val card4: Card = cards.Card(Rank.Queen(), Suit.Diamonds())

    val hand: Hand = Hand(List(), List())
    assert(hand.sortByValue(card3, card4, card1, card2) equals List(card1, card2, card4, card3))
  }

  test("Sort cards by suit") {
    val card1: Card = cards.Card(Rank.Two(), Suit.Clubs())
    val card2: Card = cards.Card(Rank.Four(), Suit.Spades())
    val card3: Card = cards.Card(Rank.King(), Suit.Hearts())
    val card4: Card = cards.Card(Rank.Queen(), Suit.Diamonds())

    val hand: Hand = Hand(List(), List())
    assert(hand.sortBySuit(card1, card2, card3, card4) equals List(card3, card4, card1, card2))
  }
}
