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
   * Convert string
   *
   * @param term term to convert and replace
   * @return string to return
   */
  def toStringAndReplace(term: Term): String

  /**
   * Convert string to term
   *
   * @param string string to convert
   * @return type term to return
   */
  def toTerm(string: String): Term

  /**
   * Convert string to term list
   *
   * @param string string to convert
   * @return type term to return
   */
  def toTermList(string: String): Term

  /**
   * Convert a list of cards in a tuple list of string
   *
   * @param cards cards to convert
   * @return type string
   */
  def toTupleList(cards: List[Card]): String
}

/**
 * Object to initialize the class PrologGameConverter
 */
object PrologConverter {

  def apply(): PrologGameConverter = new PrologGameConverter
}
