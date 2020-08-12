package it.parttimeteam.Prolog.converter

import alice.tuprolog.Term
import it.parttimeteam.Card

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
   * Convert string to term
   *
   * @param string string to convert
   * @return term to return
   */
  def toTerm(string: String): Term

  /**
   * Convert tuple-list to term
   *
   * @param tupleList tuple-list to convert
   * @return term to return
   */
  def toTermList(tupleList: List[(Int, String)]): Term

  /**
   * Convert a list of cards in a term
   *
   * @param cards cards to convert
   * @return term to return
   */
  def cardsConvert(cards: List[Card]): Term
}

/**
 * Object to initialize the class PrologGameConverter
 */
object PrologConverter {

  def apply(): PrologGameConverter = new PrologGameConverter
}
