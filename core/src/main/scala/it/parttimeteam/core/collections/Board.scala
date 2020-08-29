package it.parttimeteam.core.collections

import it.parttimeteam.core.cards.Card

/**
 * Represent the game board.
 *
 * @param combinations combinations of cards.
 */
case class Board(combinations: Seq[CardCombination]) {
  /**
   * Add new combination to game board.
   *
   * @param others Combinations to add.
   * @return Updated game board.
   */
  def putCombination(others: CardCombination*): Board = Board(combinations ++ others)

  /**
   * Pick a card seq from the actual game board.
   *
   * @param cards Combinations to pick up.
   * @return Error string or the updated board.
   * @todo Clean empty combinations.
   */
  def pickCards(cards: Seq[Card]): Either[String, Board] = {
    Either.cond(
      cardsInBoard(cards),
      Board(combinations.foldLeft(Seq.empty[CardCombination]) {
        (acc: Seq[CardCombination], boardCombination: CardCombination) => {
          val comb = boardCombination.pickCards(cards)
          if (comb.isEmpty) acc else comb +: acc
        }
      }.reverse),
      "No cards in the board")
  }

  /**
   * Put a card seq into the actual game board.
   *
   * @param id    Id id the combination where to put cards.
   * @param cards Cards to put.
   * @return Error string or the updated board.
   */
  def putCards(id: String, cards: Seq[Card]): Board = copy(combinations = this.combinations.map(comb =>
    if (comb.id == id) {
      comb.putCards(cards)
    } else {
      comb
    }))

  /**
   * Return if a card is present in any combination on the board or not.
   *
   * @return True if is present, false anywhere.
   */
  private def cardsInBoard(cards: Seq[Card]): Boolean = {
    val boardCards = this.combinations.flatMap(_.cards)
    cards forall (c => boardCards contains c)
  }
}

object Board {
  /**
   * Represent an empty game board.
   */
  def empty: Board = Board(Seq.empty)
}