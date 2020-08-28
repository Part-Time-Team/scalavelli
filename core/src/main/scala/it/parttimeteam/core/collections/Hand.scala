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
    (cards exists (c => playerCards contains c)) ||
      (cards exists (c => tableCards contains c))

  /**
   * Remove cards contained in the hand or return an error string if the hand
   * doesn't contain all the cards in the parameter.
   *
   * @param cards Cards to remove from Hand.
   * @return
   */
  def removeCards(cards: Seq[Card]): Either[String, (Hand, Seq[Card])] = {

    /**
     * Remove cards contained in the hand.
     *
     * @param cards Cards that must be removed from the hand.
     * @return New hand without those cards.
     */
    def remove(cards: Seq[Card]): (Hand, Seq[Card]) = {
      val removedPlayerCards = playerCards.foldLeft((Seq.empty[Card], Seq.empty[Card])) {
        (acc: (Seq[Card], Seq[Card]), card) => {
          // If cards contains card
          if (cards contains card)
            (acc._1, acc._2 ++ (card +: Nil)) // I remove it from hand and put it into removed cards.
          else
            (acc._1 ++ (card +: Nil), acc._2) // I re-add it to playerCards.
        }
      }

      val removedBoardCards = tableCards.foldLeft((Seq.empty[Card], Seq.empty[Card])) {
        (acc: (Seq[Card], Seq[Card]), card) => {
          if (cards contains card)
            (acc._1, acc._2 ++ (card +: Nil))
          else
            (acc._1 ++ (card +: Nil), acc._2)
        }
      }

      // Return a new hand with new card lists and the concat of the two sequences of removed cards
      (this.copy(playerCards = removedPlayerCards._1.toList,
        tableCards = removedBoardCards._1.toList),
        removedPlayerCards._2 ++ removedBoardCards._2)
    }

    Either.cond(cards forall (c => this containsCards c), remove(cards), s"$this doesn't contain $cards")
  }
}