package it.parttimeteam

import org.scalatest.funsuite.AnyFunSuite

class HandSpec extends AnyFunSuite{

  test("Check add cards to the list playerCard"){
    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Four(), Suit.Spades())
    val card3: Card = Card(Rank.King(), Suit.Diamonds())

    val hand: Hand = Hand(List(card1))
    assert(hand.addPlayerCards(card2, card3) equals Hand(List(card1, card2, card3)))
  }

  test("Check add cards to the list tableCard"){
    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Four(), Suit.Spades())
    val card3: Card = Card(Rank.King(), Suit.Diamonds())
    val card4: Card = Card(Rank.Queen(), Suit.Diamonds())

    val hand: Hand = Hand(List(card4), List(card1))
    assert(hand.addTableCards(card3, card2) equals Hand(List(card4), List(card1, card3, card2)))
  }

  test("Get player's hand"){
    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Four(), Suit.Spades())

    val hand : Hand = Hand(List(card1, card2), List())
    assert(hand.getHand equals Hand(List(card1, card2), List()))
  }

  test("Sort cards by value"){
    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Four(), Suit.Spades())
    val card3: Card = Card(Rank.King(), Suit.Diamonds())
    val card4: Card = Card(Rank.Queen(), Suit.Diamonds())

    val hand : Hand = Hand(List(), List())
    assert(hand.sortByValue(card3, card4, card1, card2) equals List(card1, card2, card4, card3))
  }

  test("Sort cards by suit"){
    val card1: Card = Card(Rank.Two(), Suit.Clubs())
    val card2: Card = Card(Rank.Four(), Suit.Spades())
    val card3: Card = Card(Rank.King(), Suit.Hearts())
    val card4: Card = Card(Rank.Queen(), Suit.Diamonds())

    val hand : Hand = Hand(List(), List())
    assert(hand.sortBySuit(card1, card2, card3, card4) equals List(card3, card4, card1, card2))
  }
}
