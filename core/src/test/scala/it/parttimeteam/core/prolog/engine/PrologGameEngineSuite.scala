package it.parttimeteam.core.prolog.engine

import alice.tuprolog.{Prolog, Term, Var}
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import it.parttimeteam.core.prolog.converter.PrologGameConverter
import org.scalatest.funsuite.AnyFunSuite

class PrologGameEngineSuite extends AnyFunSuite {

  val prologEngine: PrologGameEngine = new PrologGameEngine
  val prolog: Prolog = new Prolog

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
