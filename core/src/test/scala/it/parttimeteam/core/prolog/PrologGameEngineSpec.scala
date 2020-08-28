package it.parttimeteam.core.prolog

import alice.tuprolog.{Prolog, Term, Var}
import it.parttimeteam.core.prolog.engine.{PrologEngine, PrologGameEngine, PrologStruct}
import org.scalatest.funsuite.AnyFunSuite

class PrologGameEngineSpec extends AnyFunSuite {

  val prologEngine: PrologGameEngine = new PrologGameEngine
  val prolog: Prolog = new Prolog

  test("Resolve a specific valid goal") {
    val result : Seq[Term] = List(prolog toTerm "♥")

    assert(prologEngine.goal(PrologStruct("suit", new Var("X"))) equals result)
    assert(prologEngine.goal("suit(X).") equals result)
  }

  test("Resolve a specific invalid goal") {
    assert(prologEngine.goal(PrologStruct("startHand")) equals Seq())
    assert(prologEngine.goal("startHand.") equals Seq())
  }

  test("Resolve a specific valid goals") {
    assert(prologEngine.goals(PrologStruct("card", new Var("X"), new Var("Y"), new Var("Z"))).nonEmpty equals true)
  }

  test("Resolve a specific invalid goals") {
    assert(prologEngine.goals(PrologStruct("card", new Var("X"), new Var("Y"))).nonEmpty equals false)
  }
}
