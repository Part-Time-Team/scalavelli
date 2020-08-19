package it.parttimeteam.core.cards

import it.parttimeteam.core.cards
import org.scalatest.funsuite.AnyFunSuite

class CardSuite extends AnyFunSuite {
  test("Naming and ShortNaming") {
    val card: Card = cards.Card(Rank.Ace(), Suit.Spades())
    assert(card.name() equals f"${Rank.Ace().name} of ${Suit.Spades().name}")
    assert(card.shortName() equals f"${Rank.Ace().shortName}${Suit.Spades().shortName}")
  }

  test("Try isNext on different Suit") {
    val h2: Card = cards.Card(Rank.Two(), Suit.Hearts())
    val s1: Card = cards.Card(Rank.Ace(), Suit.Spades())
    assert(!(h2 isNext s1))
  }

  test("Try isNext on same Suit") {
    val s2: Card = cards.Card(Rank.Two(), Suit.Spades())
    val s1: Card = cards.Card(Rank.Ace(), Suit.Spades())
    assert(s2 isNext s1)
  }

  test("Try isNext on Ace and King of the Suit") {
    val s1: Card = cards.Card(Rank.Ace(), Suit.Spades())
    val sk: Card = cards.Card(Rank.King(), Suit.Spades())
    assert(s1 isNext sk)
  }

  test("Try string2card from correct cards") {
    val h3: Card = cards.Card(Rank.Three(), Suit.Hearts())
    assert(Card string2card h3.shortName() equals h3)
  }

  test("Invoking toString on a card will produce the shortname") {
    val c4: Card = cards.Card(Rank.Four(), Suit.Clubs())
    assert(c4.toString() equals c4.shortName())
  }

  test("Invoking string2card on currect value will produce RuntimeException") {
    val s: String = "g1"
    assertThrows[RuntimeException] {
      Card string2card s
    }
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
