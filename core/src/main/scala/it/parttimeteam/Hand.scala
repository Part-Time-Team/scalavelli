package it.parttimeteam

/**
 *
 * @param playerCards
 * @param tableCards
 */
case class Hand(playerCards: List[Card] = List(), tableCards: List[Card] = List()) {

  /**
   * Add card to hand player
   * @param card card to added
   * @return new case class Hand with update specific list
   */
  def addPlayerCard(card: Card): Hand = {
    Hand(playerCards :+ card, tableCards)
  }

  /**
   * Add cards to hand player
   * @param cards cards to added
   * @return new case class Hand with update specific list
   */
  def addPlayerCards(cards: Card*): Hand = {
    Hand(playerCards ++ cards.toList, tableCards)
  }

  /**
   * Add card to table player
   * @param card card to added
   * @return new case class Hand with update specific list
   */
  def addTableCard(card :Card):Hand = {
    Hand(playerCards, tableCards :+ card)
  }

  /**
   * Add cards to table player
   * @param cards cards to added
   * @return new case class Hand with update specific list
   */
  def addTableCards(cards :Card*):Hand = {
    Hand(playerCards, tableCards ++ cards.toList)
  }


  /*
  def orderBySuit()
  def orderByValue()
  */
}
