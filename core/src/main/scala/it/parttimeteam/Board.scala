package it.parttimeteam

import it.parttimeteam.core.collections.CardCombination

/**
 * Represent the game board
 *
 * @param combinations combinations of cards
 */
case class Board(combinations: List[CardCombination]) {
  /**
   * Add new combination to game board.
   *
   * @param others Combinations to add.
   * @return Updated game board.
   */
  def addCombination(others: CardCombination*): Board =
    Board(combinations ++ others)

  /**
   * Pick a combination from the actual game board.
   *
   * @param combinations Combinations to pick up.
   * @return
   */
  def pickCombination(combinations: CardCombination*): Board = {
    Board((combinations filterNot (p => p equals combinations)).toList)
  }
}

object Board {
  /**
   * Represent an empty game board.
   */
  def empty: Board = Board(List.empty)
}