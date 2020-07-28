package it.parttimeteam

/**
 * Represent the game board
 *
 * @param combinationsList combinations of cards
 */
case class GameBoard(combinationsList: List[CardCombination] = List()) {

  /**
   * Add new combination to game board
   *
   * @param combinations combinations to add
   * @return updated game board
   */
  def addCombination(combinations: CardCombination*): GameBoard = {
    GameBoard(combinationsList ++ combinations)
  }
}
