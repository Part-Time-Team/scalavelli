package it.parttimeteam.core.collections

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.prolog.PrologGame

// TODO: Remove the default value.
/**
 * Represent a combination of cards. Can be a Quarter or a Chair.
 * A Combination cannot be empty.
 *
 * @param cards Combinations of cards.
 */
case class CardCombination(cards: Seq[Card] = Seq.empty) {

  /**
   * Check if the combination is valid.
   *
   * @return true if the combination is valid otherwise false.
   */
  def isValid: Boolean =
    if(cards.forall(c => c.rank equals cards.head.rank))
      PrologGame().validateQuarter(cards.toList)
    else
      PrologGame().validateSequence(cards.toList)
  // TODO: Can be a Seq or need to be a List?
}
