package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Term, Var}
import it.parttimeteam.core.cards.{Card, Rank, Suit}

/**
 * Helper facilities to improve conversions results in prolog
 */
trait PrologConverter {

  /**
   * Convert a sequence of cards in string
   *
   * @param cards cards to convert
   * @param variable optional variable present in the prolog predicate
   * @return cards sequence converted into string
   */
  def cardsConvertToString(cards: Seq[Card])(variable: Option[Var]): String

  /**
   * Convert a sequence of term in string and extract a tuple list
   *
   * @param cards cards to convert
   * @return tuple list containing rank and suit
   */
  def sortedCard(cards: Seq[Term]): Seq[(Rank, Suit)]

  /**
   * Create card entity
   *
   * @param color color of card
   * @param suit suit of card
   * @param rank rank of card
   * @return entity card
   */
  def getCard(color: Term, suit: Term, rank: Term): Card

  /**
   * Converts the value card Ace if it is after King card
   *
   * @param cards sequence of cards
   * @return new sequence of cards
   */
  def optionalValueCards(cards: Seq[Card]): Seq[Card]

  /**
   * Convert tuple sequence in a prolog list
   *
   * @param tupleCard tuple sequence to convert
   * @param variable optional variable present in the prolog predicate
   * @return prolog string list
   */
  def prologList(tupleCard: Seq[(Int, String)])(variable: Option[Var]): String
}
