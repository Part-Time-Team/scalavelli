package it.parttimeteam.core.prolog.converter

import alice.tuprolog.Term
import it.parttimeteam.core.cards.Card

/**
 * Helper facilities to improve conversions results in prolog
 */
trait PrologConverter {

  /**
   * Convert term to int
   *
   * @param term term to covert
   * @return type int to return
   */
  def toInt(term: Term): Int

  /**
   * Convert string and replace
   *
   * @param term term to convert
   * @return string to return
   */
  def toStringAndReplace(term: Term): String

  /**
   * Convert result of goal in boolean
   *
   * @param list result of goal
   * @return true if the goal is successful, false otherwise
   */
  def toBoolean(list: List[Term]): Boolean


  /**
   * Convert a list of cards in a term
   *
   * @param cards cards to convert
   * @return term to return
   */
  def cardsConvert(cards: List[Card]): String
}

/**
 * Object to initialize the class PrologGameConverter
 */
object PrologConverter {

  def apply(): PrologGameConverter = new PrologGameConverter
}
