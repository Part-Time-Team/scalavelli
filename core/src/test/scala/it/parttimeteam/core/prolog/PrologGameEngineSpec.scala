package it.parttimeteam.core.prolog

import alice.tuprolog.{Prolog, Term, Var}
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import it.parttimeteam.core.prolog.converter.PrologGameConverter
import it.parttimeteam.core.prolog.engine.{PrologGameEngine, PrologStruct}
import org.scalatest.funsuite.AnyFunSuite

class PrologGameEngineSpec extends AnyFunSuite {

  val prologEngine: PrologGameEngine = new PrologGameEngine
  val prolog: Prolog = new Prolog

  test("Check if the predicate has solution"){

    val card1: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Blue())
    val card2: Card = Card(Rank.Ace(), Suit.Spades(), Color.Blue())
    val card3: Card = Card(Rank.Ace(), Suit.Diamonds(), Color.Red())
    val conversion : PrologGameConverter = new PrologGameConverter()

    assertResult(true)(prologEngine isSuccess "validationQuarter" + conversion.cardsConvertToString(Seq(card1, card2, card3))(None))
  }

  test("Resolve a specific valid goal") {
    val result : Seq[Term] = List(prolog toTerm "♥")

    assertResult(result)(prologEngine.goal(PrologStruct("suit", new Var("X"))))
    assertResult(result)(prologEngine.goal("suit(X)."))
  }

  test("Resolve a specific invalid goal") {
    assertResult(Seq())(prologEngine.goal(PrologStruct("startHand")))
    assertResult(Seq())(prologEngine.goal("startHand."))
  }

  test("Resolve a specific valid goals") {
    assertResult(true)(prologEngine.goals(PrologStruct("card", new Var("X"), new Var("Y"), new Var("Z"))).nonEmpty)
  }

  test("Resolve a specific invalid goals") {
    assertResult(false)(prologEngine.goals(PrologStruct("card", new Var("X"), new Var("Y"))).nonEmpty)
  }
}
