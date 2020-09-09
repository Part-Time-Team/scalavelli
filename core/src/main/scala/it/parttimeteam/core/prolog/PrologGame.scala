package it.parttimeteam.core.prolog

import alice.tuprolog.{Term, Var}
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.prolog.converter.PrologGameConverter
import it.parttimeteam.core.prolog.engine.{PrologGameEngine, PrologStruct}

import scala.annotation.tailrec

class PrologGame() {

  /**
   * Variable for the goals of the prolog
   */
  private val X: Var = new Var("X")
  private val Y: Var = new Var("Y")
  private val Z: Var = new Var("Z")

  /**
   * Predicate for the goals of the prolog
   */
  private val card: String = "card"
  private val validationQuarter: String = "validationQuarter"
  private val validationChain: String = "validationChain"
  private val orderByValue: String = "quicksortValue"
  private val orderBySuit: String = "quicksortSuit"

  private val conversion: PrologGameConverter = new PrologGameConverter
  private val engine: PrologGameEngine = new PrologGameEngine

  /**
   * Loading deck
   *
   * @return deck entity
   */
  def loadDeck: Seq[Card] = {

    val deckToLoad: Iterator[Seq[Term]] = engine goals PrologStruct(card, X, Y, Z) grouped 3

    @tailrec
    def _loadDeck(deck: Seq[Card])(deckToLoad: Seq[Seq[Term]]): Seq[Card] = deckToLoad match {

      case h :: t => _loadDeck(deck :+ conversion.getCard(h.head, h(1), h(2)))(t)
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
    engine isSuccess validationQuarter + conversion.cardsConvertToString(cards)(None)
  }

  /**
   * Validate sequence or scala of cards
   *
   * @param cards cards to validate
   * @return true if the goal is successful, false otherwise
   */
  def validateChain(cards: Seq[Card]): Boolean = {
    engine isSuccess validationChain + conversion.cardsConvertToString(conversion optionalValueCards cards)(None)
  }

  /**
   * Sort by value a sequence of cards
   *
   * @param cards cards to sort
   * @return ordered card sequence
   */
  def sortByRank(cards: Seq[Card]): Seq[Card] = {

    val prologResult: Seq[Term] = engine goal orderByValue + conversion.cardsConvertToString(conversion optionalValueCards cards)(Some(X))
    this.completedResult(cards, prologResult)
  }

  /**
   * Sort by suit a sequence of cards.
   *
   * @param cards cards to sort.
   * @return ordered card sequence.
   */
  def sortBySuit(cards: Seq[Card]): Seq[Card] = {
    val prologResult: Seq[Term] = engine goal orderBySuit + conversion.cardsConvertToString(cards)(Some(X))
    this.completedResult(cards, prologResult)
  }

  /**
   * Add color to ordered cards.
   *
   * @param inputCards  input cards.
   * @param sortedCards ordered cards.
   * @return ordered cards with color.
   */
  private def completedResult(inputCards: Seq[Card], sortedCards: Seq[Term]): Seq[Card] = {
    var tmpInputCards: Seq[Card] = inputCards
    conversion.sortedCard(sortedCards).map(tuple => {
      val foundCard: Card = tmpInputCards.find(card => card.rank == tuple._1 && card.suit == tuple._2).get

      tmpInputCards = tmpInputCards.filter(_ != foundCard)
      foundCard
    })
  }
}

/**
 * Object to initialize the class PrologGame
 */
object PrologGame {
  def apply(): PrologGame = new PrologGame()
}
