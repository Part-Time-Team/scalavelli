package it.parttimeteam.core.prolog

import alice.tuprolog.{Term, Var}
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import it.parttimeteam.core.prolog.converter.PrologGameConverter
import it.parttimeteam.core.prolog.engine.{PrologGameEngine, PrologStruct}

import scala.annotation.tailrec

class PrologGame() {

  private val conversion: PrologGameConverter = new PrologGameConverter
  private val engine: PrologGameEngine = new PrologGameEngine

  /**
   * Predicate for the goals of the prolog
   */
  private val card: String = "card"
  private val validationQuarter: String = "validationQuarter"
  private val validationChain: String = "validationChain"
  private val orderByValue: String = "quicksort"

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
  def loadDeck: Seq[Card] = {

    val deckToLoad: Iterator[Seq[Term]] = engine goals PrologStruct(card, x, y, z) grouped 3

    @tailrec
    def _loadDeck(deck: Seq[Card])(deckToLoad: Seq[Seq[Term]]): Seq[Card] = deckToLoad match {

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
  def validateQuarter(cards: Seq[Card]): Boolean = {

    val validate : Seq[Term] = engine goal validationQuarter + conversion.cardsConvert(cards)(None)
    conversion toBoolean validate
  }

  /**
   * Validate sequence or scala of cards
   *
   * @param cards cards to validate
   * @return true if the goal is successful, false otherwise
   */
  def validateChain(cards: Seq[Card]): Boolean = {

    val validate : Seq[Term] = engine goal validationChain + conversion.cardsConvert(cards)(None)
    conversion toBoolean validate
  }

  def orderByValue(cards: Seq[Card]): Unit = {
    println(orderByValue + conversion.cardsConvert(cards)(Some(x)))
    val sortedSeq : Seq[Term] = engine goal orderByValue + conversion.cardsConvert(cards)(Some(x))
    //TODO ottenere una stringa decente e convertirla in seq[card]
    println(conversion toStringAndReplace(sortedSeq))
  }
}

/**
 * Object to initialize the class PrologGame
 */
object PrologGame extends App {
  def apply(): PrologGame = new PrologGame()

  val game = new PrologGame()

  val queen: Card = Card(Rank.Queen(), Suit.Clubs(), Color.Blue())
  val king: Card = Card(Rank.King(), Suit.Clubs(), Color.Blue())
  val ace: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Blue())
  val two: Card = Card(Rank.Two(), Suit.Clubs(), Color.Red())

  game.orderByValue(Seq(queen, ace, king, two))
}
