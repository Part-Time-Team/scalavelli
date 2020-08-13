package it.parttimeteam

import it.parttimeteam.core.collections.CardCombination

/**
 * Represent the game board
 *
 * @param combinationsList combinations of cards
 */
case class Board(combinationsList: List[CardCombination] = List()) {

  /**
   * Add new combination to game board
   *
   * @param combinations combinations to add
   * @return updated game board
   */
  def addCombination(combinations: CardCombination*): Board = {
    this.copy(combinationsList = combinationsList ++ combinations)
  }

  def pickCombination(combinations: CardCombination*): Board = ???
}
