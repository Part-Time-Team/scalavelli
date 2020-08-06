package it.parttimeteam.Prolog

import java.io.InputStream
import java.util

import alice.tuprolog.{Term, Var}
import it.parttimeteam.{Card, Deck}

case class DeckImpl() {

  val theory: InputStream = getClass.getResourceAsStream("rules.pl")
  val engine: PrologEngine = PrologEngine.loadTheory(theory)

  val cardsPredicate: String = "card"
  val suitPredicate: String = "suit"
  val shuffledDeck : String = "random_permutation"

  val x: Var = new Var("X")
  val y: Var = new Var("Y")
  val z: Var = new Var("Z")

  /**
   * Loading suit from predicate in prolog
   *
   * @return Suit of deck
   */
  def loadSuit: Iterator[List[Term]] = {

    engine goals PrologStruct(suitPredicate, x, y) grouped 2

    // TODO creare oggetti Suit. Apply nel object?
    //var suit :List[Suit] = List()
    //iteratorSuite.foreach( item => item)
    //suit
  }

  /**
   * Loading deck from predicate in prolog
   *
   * @return deck entity
   */
  def loadDeck: Iterator[List[Term]] = {

    engine goals PrologStruct(cardsPredicate, x, y, z) grouped 3
    // TODO come creo le carte con suit e rank??
    /*var cardsList: List[Card] = List()
    cards.foreach(item => {
      cardsList = Card() :: cardsList
    })
    Deck(cardsList)*/
  }
}

object DeckProlog extends App {

  val deckImpl: DeckImpl = DeckImpl()

  deckImpl.loadSuit.foreach(println(_))
  deckImpl.loadDeck.foreach(println(_))
}
