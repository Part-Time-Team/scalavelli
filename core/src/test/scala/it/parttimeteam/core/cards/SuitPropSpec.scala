package it.parttimeteam.core.cards

import it.parttimeteam.core.cards.Suit.{Clubs, Diamonds, Hearts, Spades}
import org.scalatest.matchers.should
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

class SuitPropSpec
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

  property("Check comparation between suits") {
    forAll(examples) { suit =>
      suit compareTo Diamonds() should be(suit.order compareTo Diamonds().order)
    }
  }
}