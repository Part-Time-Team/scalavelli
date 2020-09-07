package it.parttimeteam.core.cards

import it.parttimeteam.core.cards.Rank._
import org.scalatest.funsuite.AnyFunSuite

class RankSuite extends AnyFunSuite {
  test("Check all ranks properties") {
    assert(Ace().name equals "Ace")
    assert(Ace().shortName equals "1")
    assert(Ace().value equals 1)

    assert(Two().name equals "Two")
    assert(Two().shortName equals "2")
    assert(Two().value equals 2)

    assert(Three().name equals "Three")
    assert(Three().shortName equals "3")
    assert(Three().value equals 3)

    assert(Four().name equals "Four")
    assert(Four().shortName equals "4")
    assert(Four().value equals 4)

    assert(Five().name equals "Five")
    assert(Five().shortName equals "5")
    assert(Five().value equals 5)

    assert(Six().name equals "Six")
    assert(Six().shortName equals "6")
    assert(Six().value equals 6)

    assert(Seven().name equals "Seven")
    assert(Seven().shortName equals "7")
    assert(Seven().value equals 7)

    assert(Eight().name equals "Eight")
    assert(Eight().shortName equals "8")
    assert(Eight().value equals 8)

    assert(Nine().name equals "Nine")
    assert(Nine().shortName equals "9")
    assert(Nine().value equals 9)

    assert(Ten().name equals "Ten")
    assert(Ten().shortName equals "10")
    assert(Ten().value equals 10)

    assert(Jack().name equals "Jack")
    assert(Jack().shortName equals "11")
    assert(Jack().value equals 11)

    assert(Queen().name equals "Queen")
    assert(Queen().shortName equals "12")
    assert(Queen().value equals 12)

    assert(King().name equals "King")
    assert(King().shortName equals "13")
    assert(King().value equals 13)

    assert(OverflowAce().name equals "Ace")
    assert(OverflowAce().shortName equals "14")
    assert(OverflowAce().value equals 14)
  }

  test("Compare King and Ace ranks") {
    assert(King() compareTo Queen() equals 1)
    assert(King() compareTo King() equals 0)
    assert(King() compareTo Ace() equals -1)
  }

  test("Compare Ace and King ranks") {
    assert(Ace() compareTo King() equals 1)
    assert(Ace() compareTo Ace() equals 0)
    assert(Ace() compareTo Two() equals -1)
  }

  test("Check all string2rank properties"){
    assert(Rank.string2rank(ACE) equals Ace())
    assert(Rank.string2rank(OVERFLOW_ACE) equals OverflowAce())
    assert(Rank.string2rank(TWO) equals Two())
    assert(Rank.string2rank(THREE) equals Three())
    assert(Rank.string2rank(FOUR) equals Four())
    assert(Rank.string2rank(FIVE) equals Five())
    assert(Rank.string2rank(SIX) equals Six())
    assert(Rank.string2rank(SEVEN) equals Seven())
    assert(Rank.string2rank(EIGHT) equals Eight())
    assert(Rank.string2rank(NINE) equals Nine())
    assert(Rank.string2rank(TEN) equals Ten())
    assert(Rank.string2rank(JACK) equals Jack())
    assert(Rank.string2rank(QUEEN) equals Queen())
    assert(Rank.string2rank(KING) equals King())
  }

  test("Check Equals with other obs") {
    assert(!(Rank.Ace().name equals "A2"))
  }
}