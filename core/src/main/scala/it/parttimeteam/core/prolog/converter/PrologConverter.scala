package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Term, Var}
import it.parttimeteam.core.cards.Card

/**
 * Helper facilities to improve conversions results in prolog
 */
trait PrologConverter {

  /**
   * Convert string and replace
   *
   * @param term term to convert
   * @param replace sequence of characters to replace
   * @return string to return
   */
  def toString(term: Term, replace: String): String

  /**
   * Convert result of goal in boolean
   *
   * @param seq result of goal
   * @return true if the goal is successful, false otherwise
   */
  def resultToBoolean(seq: Seq[Term]): Boolean


  /**
   * Convert a sequence of cards in a term
   *
   * @param cards cards to convert
   * @param variable
   * @return term to return
   */
  def cardsConvertToString(cards: Seq[Card])(variable: Option[Var]): String
}
