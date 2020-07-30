package it.parttimeteam

import it.parttimeteam.Rank.{Ace, King}

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
  def isNext(card: Card): Boolean = (rank, suit, card.rank, card.suit) match {
    case (_, suit, _, cardSuite) if !(suit.name equals cardSuite.name) => false
    case (Ace(), _, King(), _) => true
    case _ => card.rank.value + 1 equals rank.value
  }

  override def toString(): String = shortName()

  override def compareTo(t: Card): Int = (suit, t.suit) match {
    case (suit, tSuit) if suit equals tSuit => this.rank compareTo t.rank
    case _ => suit compareTo t.suit
  }
}

object Card {
  private val pattern = "^([AJKQ2-9])\\s*([♣♠♦♥])$".r

  def fullDeck: List[Card] = for {
    suit <- Suit.all
    rank <- Rank.all
  } yield Card(rank, suit)

  implicit def string2card(s: String): Card = s match {
    case pattern(rank, suit) => Card(rank, suit)
    case _ => throw new RuntimeException(s"Invalid card string ${s}")
  }
}
