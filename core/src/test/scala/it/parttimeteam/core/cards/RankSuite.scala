package it.parttimeteam.core.cards

import it.parttimeteam.core.cards.Rank._
import org.scalatest.funsuite.AnyFunSuite

class RankSuite extends AnyFunSuite {
  test("Check all ranks properties") {
    assert(Ace().name equals "Ace")
    assert(Ace().shortName equals "A")
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
    assert(Ten().shortName equals "0")
    assert(Ten().value equals 10)

    assert(Jack().name equals "Jack")
    assert(Jack().shortName equals "J")
    assert(Jack().value equals 11)

    assert(Queen().name equals "Queen")
    assert(Queen().shortName equals "Q")
    assert(Queen().value equals 12)

    assert(King().name equals "King")
    assert(King().shortName equals "K")
    assert(King().value equals 13)
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

  test("Check Equals with other objs") {
    assert(!(Rank.Ace().name equals "A2"))
  }
}