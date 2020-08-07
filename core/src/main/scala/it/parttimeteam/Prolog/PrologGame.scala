package it.parttimeteam.Prolog

import java.io.InputStream

import alice.tuprolog.{Term, Var}
import it.parttimeteam.{Card, Rank, Suit}

import scala.annotation.tailrec

case class PrologGame() {

  private val theory: InputStream = getClass.getResourceAsStream("rules.prolog")
  private val engine: PrologEngine = PrologEngine.loadTheory(theory)

  /**
   * Predicate for the goals of the prolog
   */
  private val cardsPredicate: String = "card"
  private val suitPredicate: String = "suit"
  private val startHandPredicate: String = "startHand"

  /**
   * Variable for the goals of the prolog
   */
  private val x: Var = new Var("X")
  private val y: Var = new Var("Y")
  private val z: Var = new Var("Z")

  /**
   * Loading suit from predicate in prolog
   *
   * @return Suit of deck
   */
  def loadSuit(): List[Suit] = {

    val suitToLoad: List[Term] = engine goals PrologStruct(suitPredicate, x)

    @tailrec
    def _loadSuit(suit: List[Suit])(suitToLoad: List[Term]): List[Suit] = suitToLoad match {

      case h :: t => _loadSuit(Suit.string2suit(h.toString replace("'", "")) +: suit)(t)
      case _ => suit
    }

    _loadSuit(List())(suitToLoad)
  }

  /**
   * Loading deck from predicate in prolog
   *
   * @return deck entity
   */
  def loadDeck(): List[Card] = {

    val deckToLoad: Iterator[List[Term]] = engine goals PrologStruct(cardsPredicate, x, y) grouped 2

    @tailrec
    def _loadDeck(deck: List[Card])(deckToLoad: List[List[Term]]): List[Card] = deckToLoad match {

      case h :: t => _loadDeck(Card(Rank.string2rank(h(1).toString replace("'", "")), Suit.string2suit(h.head.toString replace("'", ""))) +: deck)(t)
      case _ => deck
    }

    _loadDeck(List())(deckToLoad.toList)
  }

  /**
   * Returns the initial number of cards for each player
   *
   * @return number of cards for each player
   */
  def startHand(): Int = {
    val startHand = engine goals PrologStruct(startHandPredicate, x)
    engine toInt startHand.head
  }

}

object PrologGame extends App {

  def apply(): PrologGame = new PrologGame()

  //val game = new PrologGame()
  //game.startHand()
}
