package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Term, Var}
import it.parttimeteam.core.cards.Card

/**
 * Helper facilities to improve conversions results in prolog
 */
trait PrologConverter {

  /**
   * Convert a sequence of cards in string
   *
   * @param cards    cards to convert
   * @param variable optional variable present in the prolog predicate
   * @return cards sequence converted into string
   */
  def cardsConvertToString(cards: Seq[Card])(variable: Option[Var]): String

  /**
   * Convert a sequence of term in string and extract a card
   *
   * @param cards cards to convert
   * @return card
   */
  def sortedCard(cards: Seq[Term]): Seq[Card]

  /**
   * Create card entity
   *
   * @param color color of card
   * @param suit  suit of card
   * @param rank  rank of card
   * @return entity card
   */
  def getCard(color: Term, suit: Term, rank: Term): Card

  /**
   * It replaces the Ace value card in the Overflow Ace card in specific cases
   *
   * @param cards sequence of cards
   * @return new sequence of cards
   */
  def optionalValueAce(cards: Seq[Card]): Seq[Card]

  /**
   * Convert tuple sequence in a prolog list
   *
   * @param tupleCard tuple sequence to convert
   * @param variable  optional variable present in the prolog predicate
   * @return prolog string list
   */
  def prologList(tupleCard: Seq[(Int, String, String)])(variable: Option[Var]): String
}
