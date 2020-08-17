package it.parttimeteam

import it.parttimeteam.core.collections.CardCombination

sealed class Board(combinations: List[CardCombination]) {
  /**
   * Add new combination to game board.
   *
   * @param others Combinations to add.
   * @return Updated game board.
   */
  def addCombination(others: CardCombination*): Board =
    Board.BoardFilled(combinations ++ others)

  /**
   * Pick a combination from the actual game board.
   *
   * @param combinations Combinations to pick up.
   * @return
   */
  def pickCombination(combinations: CardCombination*): Board = ???
}

object Board {

  /**
   * Represent an empty game board.
   */
  case class EmptyBoard() extends Board(List.empty)

  /**
   * Represent the game board
   *
   * @param combinations combinations of cards
   */
  case class BoardFilled(combinations: List[CardCombination]) extends Board(combinations)

}