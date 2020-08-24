package it.parttimeteam.core.prolog

import alice.tuprolog.{Term, Var}
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import it.parttimeteam.core.prolog.converter.{PrologConverter, PrologGameConverter}
import it.parttimeteam.core.prolog.engine.{PrologEngine, PrologGameEngine, PrologStruct}

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
  def loadDeck: List[Card] = {

    val deckToLoad: Iterator[List[Term]] = engine goals PrologStruct(card, x, y, z) grouped 3

    @tailrec
    def _loadDeck(deck: List[Card])(deckToLoad: List[List[Term]]): List[Card] = deckToLoad match {

      case h :: t => _loadDeck(Card(Rank.string2rank(conversion toStringAndReplace h(2)),
        Suit.string2suit(conversion toStringAndReplace h(1)), Color.string2color(conversion toStringAndReplace h.head)) +: deck)(t)
      case _ => deck
    }

    _loadDeck(List())(deckToLoad.toList)
  }

  /**
   * Validate tris or quarter of cards.
   *
   * @param cards Cards to validate.
   * @return True if the goal is successful, False otherwise.
   */
  def validateQuarter(cards: List[Card]): Boolean = {
    val validate = engine goal validationQuarter + conversion.cardsConvert(cards)
    conversion toBoolean validate
  }

  // TODO: Rename this method into validateChair.
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
object PrologGame {
  def apply(): PrologGame = new PrologGame()
}
