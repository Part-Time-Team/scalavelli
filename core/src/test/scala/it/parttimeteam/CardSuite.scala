package it.parttimeteam

import org.scalatest.funsuite.AnyFunSuite

class CardSuite extends AnyFunSuite {
  test("Naming and ShortNaming") {
    val card: Card = Card(Rank.Ace(), Suit.Spades())
    assert(card.name() equals f"${Rank.Ace().name} of ${Suit.Spades().name}")
    assert(card.shortName() equals f"${Rank.Ace().shortName}${Suit.Spades().shortName}")
  }

  test("Try isNext on different Suit") {
    val h2: Card = Card(Rank.Two(), Suit.Hearts())
    val s1: Card = Card(Rank.Ace(), Suit.Spades())
    assert(!(h2 isNext s1))
  }

  test("Try isNext on same Suit") {
    val s2: Card = Card(Rank.Two(), Suit.Spades())
    val s1: Card = Card(Rank.Ace(), Suit.Spades())
    assert(s2 isNext s1)
  }

  test("Try isNext on Ace and King of the Suit") {
    val s1: Card = Card(Rank.Ace(), Suit.Spades())
    val sk: Card = Card(Rank.King(), Suit.Spades())
    assert(s1 isNext sk)
  }

  test("Try string2card from correct cards") {
    val h3: Card = Card(Rank.Three(), Suit.Hearts())
    assert(Card string2card h3.shortName() equals h3)
  }
}

/*
This test take too much time to reach 100 passed tests.
import org.scalacheck.Prop.{forAll, propBoolean}

object CardPropertySpec extends Properties("Cards") {
  property("Check if a card is next of another one") = forAll {
    (a: Int) =>
      (a >= 1 && a < 14) ==>
        (Card(Rank.value2rank(a % 13 + 1), Suit.Spades()).isNext(
            Card(Rank.value2rank(a), Suit.Spades())))
  }
}*/
