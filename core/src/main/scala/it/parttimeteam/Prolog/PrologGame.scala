package it.parttimeteam.Prolog

import alice.tuprolog.{Term, Var}
import it.parttimeteam.Prolog.converter.{PrologConverter, PrologGameConverter}
import it.parttimeteam.Prolog.engine.{PrologEngine, PrologGameEngine, PrologStruct}
import it.parttimeteam.{Card, Rank, Suit}

import scala.annotation.tailrec

class PrologGame() {

  private val conversion: PrologGameConverter = PrologConverter()
  private val engine: PrologGameEngine = PrologEngine()

  /**
   * Predicate for the goals of the prolog
   */
  private val card: String = "card"
  private val startHand: String = "startHand"
  private val validationQuarter: String = "validationQuarter"
  private val validationSequence: String = "validationSequence"

  /**
   * Variable for the goals of the prolog
   */
  private val x: Var = new Var("X")
  private val y: Var = new Var("Y")

  /**
   * Loading deck
   *
   * @return deck entity
   */
  def loadDeck(): List[Card] = {

    val deckToLoad: Iterator[List[Term]] = engine goals PrologStruct(card, x, y) grouped 2

    @tailrec
    def _loadDeck(deck: List[Card])(deckToLoad: List[List[Term]]): List[Card] = deckToLoad match {

      case h :: t => _loadDeck(Card(Rank.string2rank(conversion toStringAndReplace h(1)),
        Suit.string2suit(conversion toStringAndReplace h.head)) +: deck)(t)
      case _ => deck
    }

    _loadDeck(List())(deckToLoad.toList)
  }

  /**
   * Returns the initial number of cards for each player
   *
   * @return number of cards
   */
  def startGameHand(): Int = {
    val numberCards: Set[Term] = engine goal PrologStruct(startHand, x)
    conversion toInt numberCards.head
  }

  /**
   * Validate tris o quarter
   *
   * @param cards cards to validate
   */
  def validateQuarter(cards: List[Card]): Unit = {
    val tupleCards: String = conversion toTupleList cards
    //val validate: List[Term] = engine goals PrologStruct(validationQuarter, conversion toTermList tupleCards, x)
  }
}

/**
 * Object to initialize the class PrologGame
 */
object PrologGame extends App {

  def apply(): PrologGame = new PrologGame()

  val game = new PrologGame()

  val card1: Card = Card(Rank.Ace(), Suit.Clubs())
  val card2: Card = Card(Rank.Four(), Suit.Spades())
  val card3: Card = Card(Rank.King(), Suit.Diamonds())
  val card4: Card = Card(Rank.Queen(), Suit.Diamonds())

  game.validateQuarter(List(card1, card2, card3, card4))
}
