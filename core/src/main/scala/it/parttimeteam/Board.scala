package it.parttimeteam

import it.parttimeteam.core.cards.Card
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
   * @param cards Combinations to pick up.
   * @return
   */
  def pickCards(cards: Seq[Card]): Either[String, Board] = {
    def cardsInBoard(): Boolean = {
      val boardCards = this.combinations.flatMap(_.cards)
      cards forall (c => boardCards contains c)
    }

    Either.cond(
      cardsInBoard(),
      Board(combinations.foldLeft(Seq.empty[CardCombination]) {
        (acc: Seq[CardCombination], boardCombination: CardCombination) => {
          val nBoard = boardCombination.cards.filterNot(c => cards contains c)
          boardCombination.copy(cards = nBoard) +: acc
        }
      }.toList),
      "No cards in the board")
  }
}

object Board {
  /**
   * Represent an empty game board.
   */
  def empty: Board = Board(List.empty)
}