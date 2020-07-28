package it.parttimeteam

/**
 * Represents a possible combination of cards
 *
 * @param cards combinations of cards
 */
case class CardCombination(cards: List[Card] = List()) {

  /**
   * Check if the combination is valid
   *
   * @return true if the combination is valid otherwise false
   */
  def isValidate: Boolean = true
}
