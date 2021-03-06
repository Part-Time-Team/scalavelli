package it.parttimeteam.core.collections

import it.parttimeteam.core.GameError
import it.parttimeteam.core.cards.Card

/**
 * Player's Hand.
 *
 * @param playerCards Sequence of cards in the player's Hand from the turn start.
 * @param boardCards  Sequence of cards in the player's Hand picked from Board.
 */
case class Hand(playerCards: Seq[Card] = Seq.empty, boardCards: Seq[Card] = Seq.empty) {
  /**
   * Add cards to the list playerCards.
   *
   * @param cards Cards to add.
   * @return New Hand with the updated playerCards list.
   */
  def addPlayerCards(cards: Seq[Card]): Hand = this.copy(playerCards = playerCards ++ cards)

  /**
   * Add cards to the list boardCards.
   *
   * @param cards Cards to add.
   * @return New Hand with the updated boardCards list.
   */
  def addBoardCards(cards: Seq[Card]): Hand = this.copy(boardCards = boardCards ++ cards)

  /**
   * Check if hand contain some Cards.
   *
   * @param cards Cards that must be contained in the Hand.
   * @return True if all Cards are contained, false anywhere.
   */
  def containsCards(cards: Card*): Boolean =
    (cards exists (c => playerCards contains c)) ||
      (cards exists (c => boardCards contains c))

  /**
   * Remove cards contained in the Hand or return an error string if the Hand
   * doesn't contain all the Cards in the parameter.
   *
   * @param cards Cards to remove from Hand.
   * @return New Hand without Cards or a string with the error.
   */
  def removeCards(cards: Seq[Card]): Either[GameError, Hand] = {
    val updatePlayerCards: Seq[Card] = playerCards.filterNot(card => cards contains card)
    val updateBoardCards: Seq[Card] = boardCards.filterNot(card => cards contains card)

    Either.cond(cards forall (c => this containsCards c),
      this.copy(playerCards = updatePlayerCards, boardCards = updateBoardCards),
      GameError.HandNotContainCard)
  }

  /**
   * Sort each Card Sequence in Hand by Rank.
   *
   * @return Hand with Sequences ordered.
   */
  def sortByRank(): Hand =
    this.copy(
      playerCards = playerCards.sortByRank(),
      boardCards = boardCards.sortByRank()
    )

  /**
   * Sort each Card Sequence in Hand by Suit.
   *
   * @return Hand with Sequences ordered.
   */
  def sortBySuit(): Hand =
    this.copy(
      playerCards = playerCards.sortBySuit(),
      boardCards = boardCards.sortBySuit()
    )
}