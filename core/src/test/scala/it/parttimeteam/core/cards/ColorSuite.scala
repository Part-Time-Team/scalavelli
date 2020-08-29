package it.parttimeteam.core.cards

import it.parttimeteam.core.cards.Color.{Blue, Red}
import org.scalatest.funsuite.AnyFunSuite

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