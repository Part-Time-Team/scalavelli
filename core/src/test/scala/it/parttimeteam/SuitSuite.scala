package it.parttimeteam

import it.parttimeteam.Suit.{Clubs, Diamonds, Hearts, Spades}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

object SuitSuite extends AnyFunSuite {
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

object SuitPropSpec
  extends AnyPropSpec
    with TableDrivenPropertyChecks
    with should.Matchers {

  // Property test for all suits.
  val examples: TableFor1[Suit] = Table(
    "suit",
    Clubs(),
    Spades(),
    Diamonds(),
    Hearts()
  )

  val failures: TableFor1[String] = Table(
    "suit",
    "s",
    "A",
    "J",
    "Q",
    "K"
  )

  property("string2suit must return shortName at name input") {
    forAll(examples) { suit =>
      (Suit string2suit suit.shortName).name should be(suit.name)
    }
  }

  property("Invoking string2suit with other strings must return RuntimeException") {
    forAll(failures) { fail =>
      a[RuntimeException] should be thrownBy {
        Suit string2suit fail
      }
    }
  }
}