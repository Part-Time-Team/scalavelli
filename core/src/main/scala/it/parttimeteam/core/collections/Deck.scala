package it.parttimeteam.core.collections

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.prolog.PrologGame

import scala.util.Random

case class Deck(cards: List[Card]) {
  /**
   * Draw a card from the top of the deck and return it.
   *
   * @return Tuple (new Deck, Card drawn).
   */
  def draw(): (Deck, Card) = (Deck(cards.tail), cards.head)

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

  /**
   * Convert to string all cards in the deck.
   *
   * @return
   */
  override def toString: String = cards.mkString(", ")
}

object Deck {
  /**
   * Return a sorted list of cards.
   *
   * @return List of cards.
   */
  def sorted: Deck = Deck(PrologGame().loadDeck)

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
  def empty: Deck = Deck(List[Card]())

  /**
   * Convert a list of cards into a deck entity.
   *
   * @param cards List of cards.
   * @return Deck entity.
   */
  implicit def cards2deck(cards: List[Card]): Deck = Deck(cards)
}
