package it.parttimeteam.core.collections

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.prolog.PrologGame

import scala.util.Random

case class Deck(cards: Seq[Card]) {
  /**
   * Draw a card from the top of the deck and return it.
   *
   * @return Tuple (new Deck, Card drawn).
   */
  def draw(): (Deck, Option[Card]) = if (cards.isEmpty)
    (this, None)
  else
    (copy(cards = cards.tail), Some(cards.head))

  /**
   * Draw any number of cards from the top of the deck and return them.
   *
   * @param number Number of cards to draw.
   * @return Tuple(new Deck, Cards drawn).
   */
  def draw(number: Int): (Deck, Seq[Card]) = (0 until number).foldLeft((this, Seq.empty[Card])) {
    (acc, _) => {
      acc._1 draw() match {
        case (deck, Some(card)) => (deck, card +: acc._2)
        case (deck, None) => (deck, acc._2)
      }
    }
  }

  /**
   * The remaining cards number.
   *
   * @return Number of cards remaining.
   */
  def remaining: Int = cards.length

  /**
   * Check if the deck is empty.
   *
   * @return True if is empty, false anywhere.
   */
  def isEmpty: Boolean = cards.isEmpty
}

object Deck {
  /**
   * Return a sorted list of cards.
   *
   * @return List of cards.
   */
  def sorted: Deck = {
    val deck = PrologGame().loadDeck
    Deck(deck)
  }

  /**
   * Return a shuffled list of cards.
   *
   * @return List of cards.
   */
  def shuffled: Deck = Deck(Random.shuffle(PrologGame().loadDeck))

  /**
   * Return an empty list of cards.
   *
   * @return List of cards.
   */
  def empty: Deck = Deck(Seq.empty)

  /**
   * Convert a list of cards into a deck entity.
   *
   * @param cards List of cards.
   * @return Deck entity.
   */
  implicit def cards2deck(cards: Seq[Card]): Deck = Deck(cards)
}
