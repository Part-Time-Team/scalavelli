package it.parttimeteam.Prolog

import alice.tuprolog.{Term, Var}
import it.parttimeteam.Prolog.converter.PrologGameConverter
import it.parttimeteam.Prolog.engine.{PrologGameEngine, PrologStruct}
import org.scalatest.funsuite.AnyFunSuite

class PrologGameConverterSpec extends AnyFunSuite{

  test("Convert term to int"){

    val engine : PrologGameEngine = new PrologGameEngine
    val gameConverter: PrologGameConverter = new PrologGameConverter

    val numberCard :List[Term] = engine goal PrologStruct("startHand", new Var("X"))
    assert(gameConverter.toInt(numberCard.head) equals 13)
  }

  test("Convert term to boolean"){}

  test("Convert term to string"){}

  test("Convert list of cards to string"){}
}
