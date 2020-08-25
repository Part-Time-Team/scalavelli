package it.parttimeteam.core.cards

import it.parttimeteam.core.cards.Color.{Blue, Red}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

class ColorSuite extends AnyFunSuite {

  test("Check all color properties") {
    val red: Red = Color.Red()
    val blue: Blue = Color.Blue()
    assertResult("Red")(red.name)
    assertResult("R")(red.shortName)
    assertResult("Blue")(blue.name)
    assertResult("B")(blue.shortName)
  }
}

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