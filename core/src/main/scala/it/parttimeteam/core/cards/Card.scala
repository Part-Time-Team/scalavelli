package it.parttimeteam.core.cards

import it.parttimeteam.core.cards
import it.parttimeteam.core.cards.Rank.{Ace, King}
import it.parttimeteam.core.prolog.PrologGame

/**
 * Card of deck.
 *
 * @param rank Value of the card.
 * @param suit Suit.
 */
case class Card(rank: Rank, suit: Suit, color: Color)
  extends Comparable[Card] {
  /**
   * Get the full name of the card.
   *
   * @return Card full name.
   */
  def name: String = f"${rank.name} of ${suit.name} of ${color.name}"

  /**
   * Get the short name of the card.
   *
   * @return Card short name.
   */
  def shortName: String = f"${rank.shortName}${suit.shortName}${color.shortName}"

  /**
   * Check if the card is the successor of the other card.
   *
   * @param card Other card.
   * @return True if is next, false anywhere.
   */
  def isNext(card: Card): Boolean = (rank, card.rank) match {
    case (Ace(), King()) => true
    case _ if !(suit equals card.suit) => false
    case _ => card.rank.value + 1 == rank.value
  }

  override def toString: String = shortName

  override def compareTo(t: Card): Int =
    if (suit == t.suit)
      this.rank compareTo t.rank
    else
      this.suit compareTo t.suit
}


object Card {
  private val pattern = "^([AJKQ2-9])\\s*([♣♠♦♥])\\s*([RB])$".r

  implicit def string2card(s: String): Card = s match {
    case pattern(rank, suit, color) => cards.Card(rank, suit, color)
    case _ => throw new RuntimeException(s"Invalid card string $s")
  }

  /**
   * Provide to a Card Sequence new functions to sort them by Rank and Suit.
   *
   * @param base Poor Card Sequence.
   */
  implicit class MyRichCardSeq(base: Seq[Card]) {
    // TODO: Use implicits to change order strategy.

    /**
     * Class PrologGame
     */
    val prologGame = new PrologGame

    /**
     * Sort first by rank, then by suit.
     *
     * @return Ordered Sequence.
     */
    def sortByRank(): Seq[Card] = this.prologGame.sortByRank(base)

    /**
     * Sort first by suit, then by rank.
     *
     * @return Ordered Sequence.
     */
    def sortBySuit(): Seq[Card] = this.prologGame.sortBySuit(base)


    def isValid(cards: Seq[Card]): Boolean =
      if (cards.forall(c => c.rank equals cards.head.rank))
        this.prologGame.validateQuarter(cards)
      else
        this.prologGame.validateChain(cards)

  }

}