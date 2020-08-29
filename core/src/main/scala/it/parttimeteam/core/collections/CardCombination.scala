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
      PrologGame().validateSequence(cards.toList)

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
}