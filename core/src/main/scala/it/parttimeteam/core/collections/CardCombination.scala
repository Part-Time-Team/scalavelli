package it.parttimeteam.core.collections

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.prolog.PrologGame

/**
 * Represent a combination of cards. Can be a Quarter or a Chair.
 * A Combination cannot be empty.
 *
 * @param id    Id of the Combination.
 * @param cards Combinations of cards.
 */
case class CardCombination(id: String, cards: Seq[Card]) {
  /**
   * Check if the combination is valid.
   *
   * @return true if the combination is valid otherwise false.
   */
  def isValid: Boolean =
    if (cards.forall(c => c.rank equals cards.head.rank))
      PrologGame().validateQuarter(cards.toList)
    else
<<<<<<< HEAD
      PrologGame().validateChain(cards.toList)

<<<<<<< Updated upstream
=======
  // TODO: Can be a Seq or need to be a List?
=======
      PrologGame().validateSequence(cards.toList)

>>>>>>> Stashed changes
  /**
   * Return if the combination is empty or not.
   *
   * @return True if combination has no cards, false anywhere.
   */
  def isEmpty: Boolean = cards.isEmpty

  /**
   * Pick some cards from the combination.
   *
   * @param cards Cards to pick up.
   * @return CardCombination without cards.
   */
  def pickCards(cards: Seq[Card]): CardCombination = copy(cards = this.cards.filterNot(c => cards contains c))

  /**
   * Put some cards into the combination.
   *
   * @param cards Cards to put in.
   * @return CardCombination with cards.
   */
  def putCards(cards: Seq[Card]): CardCombination = copy(cards = this.cards ++ cards sortBy (_.rank))
}

object CardCombination {
  def apply(id: String, cards: Seq[Card]): CardCombination = new CardCombination(id, cards.sortByRank())
<<<<<<< Updated upstream
=======
>>>>>>> dev
>>>>>>> Stashed changes
}