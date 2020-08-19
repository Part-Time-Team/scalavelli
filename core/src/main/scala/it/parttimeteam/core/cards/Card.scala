package it.parttimeteam.core.cards

import it.parttimeteam.core.cards
import it.parttimeteam.core.cards.Rank.{Ace, King}

/**
 * Card of deck.
 *
 * @param rank Value of the card.
 * @param suit Suit.
 */
case class Card(rank: Rank, suit: Suit)
  extends Comparable[Card] {
  /**
   * Get the full name of the card.
   *
   * @return Card full name.
   */
  def name(): String = f"${rank.name} of ${suit.name}"

  /**
   * Get the short name of the card.
   *
   * @return Card short name.
   */
  def shortName(): String = f"${rank.shortName}${suit.shortName}"

  /**
   * Check if the card is the successor of the other card.
   *
   * @param card Other card.
   * @return True if is next, false anywhere.
   */
  def isNext(card: Card): Boolean = (rank, card.rank) match {
    case (Ace(), King()) => true
    case _ => if (!(suit equals card.suit)) false else card.rank.value + 1 equals rank.value
  }

  override def toString: String = shortName()

  override def compareTo(t: Card): Int =
    if (suit == t.suit)
      this.rank compareTo t.rank
    else
      suit compareTo t.suit
}

object Card {
  private val pattern = "^([AJKQ2-9])\\s*([♣♠♦♥])$".r

  def fullDeck: List[Card] = for {
    suit <- Suit.all
    rank <- Rank.all
  } yield Card(rank, suit)

  implicit def string2card(s: String): Card = s match {
    case pattern(rank, suit) => cards.Card(rank, suit)
    case _ => throw new RuntimeException(s"Invalid card string ${s}")
  }
}
