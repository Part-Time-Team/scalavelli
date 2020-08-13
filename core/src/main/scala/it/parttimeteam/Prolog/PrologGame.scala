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
  private val z: Var = new Var("Z")

  /**
   * Loading deck
   *
   * @return deck entity
   */
  def loadDeck(): List[Card] = {

    val deckToLoad: Iterator[List[Term]] = engine goals PrologStruct(card, x, y, z) grouped 3

    @tailrec
    def _loadDeck(deck: List[Card])(deckToLoad: List[List[Term]]): List[Card] = deckToLoad match {

      case h :: t => _loadDeck(Card(Rank.string2rank(conversion toStringAndReplace h(2)),
        Suit.string2suit(conversion toStringAndReplace h(1))) +: deck)(t)
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
    val numberCards: List[Term] = engine goal PrologStruct(startHand, x)
    conversion toInt numberCards.head
  }

  /**
   * Validate tris or quarter of
   *
   * @param cards cards to validate
   * @return true if the goal is successful, false otherwise
   */
  def validateQuarter(cards: List[Card]): Boolean = {
    val validate = engine goal validationQuarter + conversion.cardsConvert(cards)
    conversion toBoolean validate
  }

  /**
   * Validate sequence or scala of cards
   *
   * @param cards cards to validate
   * @return true if the goal is successful, false otherwise
   */
  def validateSequence(cards: List[Card]): Boolean = {
    val validate = engine goal validationSequence + conversion.cardsConvert(cards)
    conversion toBoolean validate
  }
}

/**
 * Object to initialize the class PrologGame
 */
object PrologGame extends App {

  def apply(): PrologGame = new PrologGame()

  val game = new PrologGame()

  val card1: Card = Card(Rank.Ace(), Suit.Clubs())
  val card2: Card = Card(Rank.Ace(), Suit.Spades())
  val card3: Card = Card(Rank.Ace(), Suit.Hearts())
  val card4: Card = Card(Rank.Ace(), Suit.Diamonds())

  //game.validateQuarter(List(card1, card2, card3, card4))
  println(game.loadDeck())
}
