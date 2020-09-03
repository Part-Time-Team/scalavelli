package it.parttimeteam.core.prolog.engine

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.prolog.Cards
import it.parttimeteam.core.prolog.converter.PrologGameConverter
import org.scalatest.matchers.should
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

class PrologGameEnginePropSpec extends AnyPropSpec with TableDrivenPropertyChecks with should.Matchers {

  val VALIDATION_QUARTER = "validationQuarter"

  val prologEngine: PrologGameEngine = new PrologGameEngine
  val conversion: PrologGameConverter = new PrologGameConverter()

  /**
   * Quarter cards
   */
  val quarterCards: TableFor1[Seq[Card]] = Table(
    "sequence cards",
    Seq(Cards().ACE_CLUBS, Cards().ACE_DIAMONDS, Cards().ACE_SPADES),
    Seq(Cards().TEN_CLUBS, Cards().TEN_HEARTS, Cards().TEN_SPADES, Cards().TEN_DIAMONDS)
  )

  /**
   * Valid goal
   */
  val validGoal: TableFor1[String] = Table(
    "valid goal",
    "suit(X).",
    "color(X).",
    "card(X,Y,Z)."
  )

  /**
   * Valid Goals
   */
  val validGoals: TableFor1[String] = Table(
    "valid goals",
    "card(X,Y,Z)."
  )

  /**
   * Invalid Goal
   */
  val invalidGoal: TableFor1[String] = Table(
    "invalid goal",
    "suit.",
    "color.",
    "card(X)."
  )

  /**
   * Invalida Goals
   */
  val invalidGoals: TableFor1[String] = Table(
    "invalid goals",
    "card(X,Y)."
  )

  property("Resolve a specific valid goal") {
    forAll(validGoal) { goal =>
      prologEngine.goal(goal).nonEmpty should be(true)
    }
  }

  property("Resolve a specific invalid goal") {
    forAll(invalidGoal) { goal =>
      prologEngine.goal(goal).nonEmpty should be(false)
      prologEngine.goal(goal) should be(Seq())
    }
  }

  property("Resolve a specific valid goals") {
    forAll(validGoals) { goal =>
      prologEngine.goal(goal).nonEmpty should be(true)
    }
  }

  property("Resolve a specific invalid goals") {
    forAll(invalidGoals) { goal =>
      prologEngine.goal(goal).nonEmpty should be(false)
    }
  }

  property("Check if the predicate has solution") {
    forAll(quarterCards) { cards =>
      prologEngine isSuccess VALIDATION_QUARTER + conversion.cardsConvertToString(cards)(None) should be(true)
    }
  }
}
