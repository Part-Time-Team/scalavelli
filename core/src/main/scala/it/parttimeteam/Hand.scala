package it.parttimeteam

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
  def addPlayerCards(cards: Card*): Hand = {
    this.copy(playerCards = playerCards ++ cards.toList)
  }

  /**
   * Add cards to the list tablePlayer
   *
   * @param cards cards to add
   * @return new Hand the updated tableCards list
   */
  def addTableCards(cards: Card*): Hand = {
    this.copy(tableCards = tableCards ++ cards.toList)
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
