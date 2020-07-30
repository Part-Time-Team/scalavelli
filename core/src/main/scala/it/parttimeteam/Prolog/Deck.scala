package it.parttimeteam.Prolog

import java.io.InputStream

import alice.tuprolog.{Term, Var}

object Deck extends App {

  val theory: InputStream = getClass.getResourceAsStream("rules.pl")
  val engine: PrologEngine = PrologEngine.loadTheory(theory)

  val card: String = "card"
  val suit: String = "suit"

  val x: Var = new Var("X")
  val y: Var = new Var("Y")
  val z: Var = new Var("Z")

  var seedList: List[Term] = List()

  // TODO da ritornare oggetti Rank e Suit
  def loadSuit(): Iterator[List[Term]] = {
    engine goals PrologStruct(suit, x, y) grouped 2
  }

  // TODO da ritornare entità deck
  def loadDeck(): Iterator[List[Term]] = {
    engine goals PrologStruct(card, x, y, z) grouped 3
  }

  //println("Suit:")
  //loadSuit().foreach(println(_))

  //println("\nDeck:")
  loadDeck().foreach(println(_))
}
