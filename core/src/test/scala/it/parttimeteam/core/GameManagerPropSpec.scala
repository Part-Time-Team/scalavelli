package it.parttimeteam.core

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.CardCombination
import org.scalatest.matchers.should
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

class GameManagerPropSpec extends AnyPropSpec with TableDrivenPropertyChecks with should.Matchers {
  val gameManager: GameManager = new GameManagerImpl()

  val c1: Card = Card.string2card("2♣R")
  val c2: Card = Card.string2card("3♣B")
  val c3: Card = Card.string2card("4♣B")
  val c4: Card = Card.string2card("5♣B")
  val c5: Card = Card.string2card("5♠R")
  val c6: Card = Card.string2card("5♦B")
  val c7: Card = Card.string2card("5♥R")

  val validCombs: TableFor1[CardCombination] = Table("cardCombination",
    CardCombination("#1", Seq(c1, c2, c3)),
    CardCombination("#2", Seq(c4, c5, c6)),
    CardCombination("#3", Seq(c4, c5, c6, c7)))

  property("Test valid combinations") {
    forAll(validCombs) { comb =>
      gameManager validateCombination comb.cards should be(true)
    }
  }

  val invalidCombs: TableFor1[CardCombination] = Table("cardCombination",
    CardCombination("#4", Seq(c4, c2, c7)))

  property("Test invalid combinations") {
    forAll(invalidCombs) { comb =>
      gameManager validateCombination comb.cards should be(false)
    }
  }
}
