package it.parttimeteam

import it.parttimeteam.Rank.{Ace, Eight, Five, Four, Jack, King, Nine, Queen, Seven, Six, Ten, Three, Two}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.funsuite.AnyFunSuite

class RankSuite extends AnyFunSuite {
  test("Check all ranks properties") {
    assert(Ace().name equals "Ace")
    assert(Ace().shortName equals "A")
    assert(Ace().value equals 14)

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
    assert(Jack().shortName equals "J")
    assert(Jack().value equals 11)

    assert(Queen().name equals "Queen")
    assert(Queen().shortName equals "Q")
    assert(Queen().value equals 12)

    assert(King().name equals "King")
    assert(King().shortName equals "K")
    assert(King().value equals 13)
  }

  test("Check all ranks") {
    assert(Rank.all equals List(Ace(), Two(), Three(), Four(), Five(), Six(), Seven(), Eight(), Nine(), Ten(), Jack(), Queen(), King()))
  }
}

class RankSpec extends AnyFunSpec {
  describe("Rank conversions") {
    it("Should be Ace") {
      assert(Rank string2rank "A" equals Ace())
    }
    it("Should be Two") {
      assert(Rank string2rank "2" equals Two())
    }
    it("Should be Three") {
      assert(Rank string2rank "3" equals Three())
    }
    it("Should be Four") {
      assert(Rank string2rank "4" equals Four())
    }
    it("Should be Five") {
      assert(Rank string2rank "5" equals Five())
    }
    it("Should be Six") {
      assert(Rank string2rank "6" equals Six())
    }
    it("Should be Seven") {
      assert(Rank string2rank "7" equals Seven())
    }
    it("Should be Eight") {
      assert(Rank string2rank "8" equals Eight())
    }
    it("Should be Nine") {
      assert(Rank string2rank "9" equals Nine())
    }
    it("Should be Ten") {
      assert(Rank string2rank "0" equals Ten())
    }
    it("Should be Jack") {
      assert(Rank string2rank "J" equals Jack())
    }
    it("Should be Queen") {
      assert(Rank string2rank "Q" equals Queen())
    }
    it("Should be King") {
      assert(Rank string2rank "K" equals King())
    }

    it("Should raise RuntimeException for s string") {
      assertThrows[RuntimeException] {
        Rank.string2rank("s")
      }
    }
  }
}