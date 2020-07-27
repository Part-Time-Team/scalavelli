package it.parttimeteam

/**
 * Player's hand
 *
 * @param playerCards list of cards in the player's hand
 * @param tableCards list of cards in the player's hand to put at the table
 */
case class Hand(playerCards: List[Card] = List(), tableCards: List[Card] = List()) {

  /**
   * Add card to the list playerCards
   *
   * @param card card to add
   * @return new Hand with the updated playerCards list
   */
  def addPlayerCard(card: Card): Hand = {
    Hand(playerCards :+ card, tableCards)
  }

  /**
   * Add cards to the list playerCards
   *
   * @param cards cards to add
   * @return new Hand with the updated playerCards list
   */
  def addPlayerCards(cards: Card*): Hand = {
    Hand(playerCards ++ cards.toList, tableCards)
  }

  /**
   * Add card to the list tableCards
   *
   * @param card card to add
   * @return new Hand the updated tableCards list
   */
  def addTableCard(card :Card):Hand = {
    Hand(playerCards, tableCards :+ card)
  }

  /**
   * Add cards to the list tablePlayer
   *
   * @param cards cards to add
   * @return new Hand the updated tableCards list
   */
  def addTableCards(cards :Card*):Hand = {
    Hand(playerCards, tableCards ++ cards.toList)
  }


  /**
   * Sort cards by value
   *
   * @param cards cards to order
   * @return sorted list of cards
   */
  def sortByValue(cards : Card*): List[Card] = {
    cards.sortWith((s: Card, t: Card) => s.rank.value < t.rank.value).toList
  }

  /**
   * Sort cards by suit
   *
   * @param cards cards to order
   * @return sorted list of cards
   */
  def sortBySuit(cards: Card*): List[Card] = {
    cards.sortWith((s:Card, t:Card) => s.suit.order < t.suit.order).toList
  }
}
