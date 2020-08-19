package it.parttimeteam.Prolog

import alice.tuprolog.{Prolog, Var}
import it.parttimeteam.Prolog.engine.{PrologEngine, PrologGameEngine, PrologStruct}
import org.scalatest.funsuite.AnyFunSuite

class PrologGameEngineSpec extends AnyFunSuite {

  val prologEngine: PrologGameEngine = PrologEngine()
  val prolog: Prolog = new Prolog


  test("Resolve a specific valid goal") {
    assert(prologEngine.goal(PrologStruct("startHand", new Var("X"))) equals List(prolog toTerm "13"))
    assert(prologEngine.goal("startHand(X).") equals List(prolog toTerm "13"))
  }

  test("Resolve a specific invalid goal") {
    assert(prologEngine.goal(PrologStruct("startHand")) equals List())
    assert(prologEngine.goal("startHand.") equals List())
  }

  test("Resolve a specific valid goals") {
    assert(prologEngine.goals(PrologStruct("card", new Var("X"), new Var("Y"), new Var("Z"))).nonEmpty equals true)
  }

  test("Resolve a specific invalid goals") {
    assert(prologEngine.goals(PrologStruct("card", new Var("X"), new Var("Y"))).nonEmpty equals false)
  }
}
