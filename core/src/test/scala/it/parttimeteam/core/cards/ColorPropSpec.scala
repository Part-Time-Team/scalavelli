package it.parttimeteam.core.cards

import it.parttimeteam.core.cards.Color.{Blue, Red}
import org.scalatest.matchers.should
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

class ColorPropSpec
  extends AnyPropSpec
    with TableDrivenPropertyChecks
    with should.Matchers {

  // Property test for all suits.
  val examples: TableFor1[Color] = Table(
    "color",
    Red(),
    Blue()
  )

  val failures: TableFor1[String] = Table(
    "color",
    "s",
    "A",
    "J",
    "Q",
    "K"
  )

  property("string2color must return shortName at name input") {
    forAll(examples) { color =>
      (Color string2color color.shortName).name should be(color.name)
    }
  }

  property("Invoking string2color with other strings must return RuntimeException") {
    forAll(failures) { fail =>
      a[RuntimeException] should be thrownBy {
        Color string2color fail
      }
    }
  }
}