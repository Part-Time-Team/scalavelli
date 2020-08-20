package it.parttimeteam.core.collections

import it.parttimeteam.core.cards.Card

/**
 * Player's hand
 *
 * @param playerCards list of cards in the player's hand
 * @param tableCards  list of cards in the player's hand to put at the table
 */
case class Hand(playerCards: List[Card] = List(), tableCards: List[Card] = List()) {

  /**
   * Get player's hand
   *
   * @return player's hand
   */
  def getHand: Hand = {
    Hand(playerCards, tableCards)
  }

  /**
   * Add cards to the list playerCards
   *
   * @param cards cards to add
   * @return new Hand with the updated playerCards list
   */
  def addPlayerCards(cards: Seq[Card]): Hand = this.copy(playerCards = playerCards ::: cards.toList)

  /**
   * Add cards to the list tablePlayer
   *
   * @param cards cards to add
   * @return new Hand the updated tableCards list
   */
  def addTableCards(cards: Seq[Card]): Hand = this.copy(tableCards = tableCards ::: cards.toList)

  /**
   * Check if hand contain some cards.
   *
   * @param cards Cards that must be contained in the hand.
   * @return True if all cards are contained, false anywhere.
   */
  def containsCards(cards: Card*): Boolean =
    (cards exists (c => playerCards contains c)) &&
      (cards exists (c => tableCards contains c))

  /**
   * Remove cards contained in the hand.
   *
   * @param cards Cards that must be removed from the hand.
   * @return New hand without those cards.
   */
  def removeCards(cards: Seq[Card]): Hand = {
    val pCards = playerCards filterNot (c => cards contains c)
    val tCards = tableCards filterNot (c => cards contains c)
    this.copy(playerCards = pCards, tableCards = tCards)
  }

  /**
   * Sort cards by value
   *
   * @param cards cards to order
   * @return sorted list of cards
   */
  def sortByValue(cards: Card*): List[Card] = {
    cards.sortWith((s: Card, t: Card) => s.rank.value < t.rank.value).toList
  }

  /**
   * Sort cards by suit
   *
   * @param cards cards to order
   * @return sorted list of cards
   */
  def sortBySuit(cards: Card*): List[Card] = {
    cards.sortWith((s: Card, t: Card) => s.suit.order < t.suit.order).toList
  }
}
