package it.parttimeteam.core

import it.parttimeteam.core.TestCards._
import it.parttimeteam.core.collections.CardCombination
import org.scalatest.matchers.should
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

class GameInterfacePropSpec extends AnyPropSpec with TableDrivenPropertyChecks with should.Matchers {
  val gameInterface: GameInterface = new GameInterfaceImpl()

  val validCombs: TableFor1[CardCombination] = Table("cardCombination",
    CardCombination("#1", Seq(TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS)),
    CardCombination("#2", Seq(FIVE_SPADES, FIVE_CLUBS, FIVE_DIAMONDS)),
    CardCombination("#3", Seq(FIVE_SPADES, FIVE_CLUBS, FIVE_DIAMONDS, FIVE_HEARTS)))

  property("Test valid combinations") {
    forAll(validCombs) { comb =>
      gameInterface validateCombination comb.cards should be(true)
    }
  }

  val invalidCombs: TableFor1[CardCombination] = Table("cardCombination",
    CardCombination("#4", Seq(FIVE_SPADES, THREE_CLUBS, FIVE_HEARTS)))

  property("Test invalid combinations") {
    forAll(invalidCombs) { comb =>
      gameInterface validateCombination comb.cards should be(false)
    }
  }
}
