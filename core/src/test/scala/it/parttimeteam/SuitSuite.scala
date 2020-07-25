package it.parttimeteam

import it.parttimeteam.Suit.{Clubs, Diamonds, Hearts, Spades}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.propspec.AnyPropSpec

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
    assert(Suit.all equals List(Clubs(), Spades(), Diamonds(), Hearts()))
  }
}

class SuitPropSpec extends AnyPropSpec with TableDrivenPropertyChecks with should.Matchers {

  val examples = Table(
    "suit",
    Clubs(),
    Spades(),
    Diamonds(),
    Hearts()
  )

  property("string2suit must return shortName at name input") {
    forAll(examples) { suit =>
      Suit.string2suit(suit.shortName).name should be (suit.name)
    }
  }
}

class SuitSpec extends AnyFunSpec {
  describe("Suit conversions") {
    it("Should be Clubs") {
      assert(Suit string2suit "♣" equals Clubs())
    }

    it("Should be Spades") {
      assert(Suit string2suit "♠" equals Spades())
    }

    it("Should be Diamonds") {
      assert(Suit string2suit "♦" equals Diamonds())
    }

    it("Should be Hearts") {
      assert(Suit string2suit "♥" equals Hearts())
    }

    it("Should raise RuntimeException for s string") {
      assertThrows[RuntimeException] {
        Suit.string2suit("s")
      }
    }
  }
}