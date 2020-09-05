package it.parttimeteam.core.collections

import it.parttimeteam.core.cards.Card

/**
 * Player's hand
 *
 * @param playerCards list of cards in the player's hand
 * @param tableCards  list of cards in the player's hand to put at the table
 */
case class Hand(playerCards: Seq[Card] = Seq.empty, tableCards: Seq[Card] = Seq.empty) {

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
  def addPlayerCards(cards: Seq[Card]): Hand = this.copy(playerCards = playerCards ++ cards)

  /**
   * Add cards to the list tablePlayer
   *
   * @param cards cards to add
   * @return new Hand the updated tableCards list
   */
  def addTableCards(cards: Seq[Card]): Hand = this.copy(tableCards = tableCards ++ cards)

  /**
   * Check if hand contain some cards.
   *
   * @param cards Cards that must be contained in the hand.
   * @return True if all cards are contained, false anywhere.
   */
  def containsCards(cards: Card*): Boolean =
    (cards exists (c => playerCards contains c)) ||
      (cards exists (c => tableCards contains c))

  /**
   * Remove cards contained in the hand or return an error string if the hand
   * doesn't contain all the cards in the parameter.
   *
   * @param cards Cards to remove from Hand.
   * @return
   */
  def removeCards(cards: Seq[Card]): Either[String, Hand] = {

    val updatePlayerCards: Seq[Card] = playerCards.filterNot(card => cards contains card)

    val updateBoardCards: Seq[Card] = tableCards.filterNot(card => cards contains card)

    Either.cond(cards forall (c => this containsCards c),
      this.copy(playerCards = updatePlayerCards, tableCards = updateBoardCards),
      "Hand doesn't contain given cards")
  }
}