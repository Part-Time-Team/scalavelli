package it.parttimeteam.core.cards

import it.parttimeteam.core.cards.Suit._
import org.scalatest.funsuite.AnyFunSuite

class SuitSuite extends AnyFunSuite {
  test("Check all suits properties") {
    assert(Clubs().name equals "Clubs")
    assert(Clubs().shortName equals "♣")

    assert(Spades().name equals "Spades")
    assert(Spades().shortName equals "♠")

    assert(Diamonds().name equals "Diamonds")
    assert(Diamonds().shortName equals "♦")

    assert(Hearts().name equals "Hearts")
    assert(Hearts().shortName equals "♥")
  }

  test("Check all suits") {
    assert(Suit.all equals List(Hearts(), Diamonds(), Clubs(), Spades()))
  }
}