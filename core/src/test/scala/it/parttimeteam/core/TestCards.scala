package it.parttimeteam.core

import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}

/**
 * Object TestCards.
 */
object TestCards {

  /**
   * Ace cards.
   */
  val ACE_CLUBS_BLUE: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Blue())
  val ACE_CLUBS_RED: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Red())
  val ACE_SPADES: Card = Card(Rank.Ace(), Suit.Spades(), Color.Blue())
  val ACE_DIAMONDS: Card = Card(Rank.Ace(), Suit.Diamonds(), Color.Red())
  val ACE_HEARTS: Card = Card(Rank.Ace(), Suit.Hearts(), Color.Red())

  /**
   * Overflow Ace cards.
   */
  val OVERFLOW_ACE_CLUBS: Card = Card(Rank.OverflowAce(), Suit.Clubs(), Color.Blue())
  val OVERFLOW_ACE_SPADES: Card = Card(Rank.OverflowAce(), Suit.Spades(), Color.Blue())
  val OVERFLOW_ACE_HEARTS: Card = Card(Rank.OverflowAce(), Suit.Hearts(), Color.Blue())

  /**
   * Two cards.
   */
  val TWO_CLUBS: Card = Card(Rank.Two(), Suit.Clubs(), Color.Blue())
  val TWO_HEARTS: Card = Card(Rank.Two(), Suit.Hearts(), Color.Red())
  val TWO_SPADES: Card = Card(Rank.Two(), Suit.Spades(), Color.Red())
  val TWO_DIAMONDS: Card = Card(Rank.Two(), Suit.Diamonds(), Color.Red())

  /**
   * Three cards.
   */
  val THREE_CLUBS: Card = Card(Rank.Three(), Suit.Clubs(), Color.Blue())
  val THREE_SPADES: Card = Card(Rank.Three(), Suit.Spades(), Color.Blue())
  val THREE_HEARTS: Card = Card(Rank.Three(), Suit.Hearts(), Color.Blue())

  /**
   * Four cards.
   */
  val FOUR_HEARTS: Card = Card(Rank.Four(), Suit.Hearts(), Color.Blue())
  val FOUR_SPADES: Card = Card(Rank.Four(), Suit.Spades(), Color.Blue())
  val FOUR_CLUBS: Card = Card(Rank.Four(), Suit.Clubs(), Color.Blue())
  val FOUR_DIAMONDS: Card = Card(Rank.Four(), Suit.Diamonds(), Color.Blue())

  /**
   * Five cards.
   */
  val FIVE_HEARTS: Card = Card(Rank.Five(), Suit.Hearts(), Color.Red())
  val FIVE_SPADES: Card = Card(Rank.Five(), Suit.Spades(), Color.Red())
  val FIVE_CLUBS: Card = Card(Rank.Five(), Suit.Clubs(), Color.Red())
  val FIVE_DIAMONDS: Card = Card(Rank.Five(), Suit.Diamonds(), Color.Red())

  /**
   * Six cards.
   */
  val SIX_HEARTS: Card = Card(Rank.Six(), Suit.Hearts(), Color.Red())
  val SIX_CLUBS: Card = Card(Rank.Six(), Suit.Clubs(), Color.Red())
  val SIX_SPADES: Card = Card(Rank.Six(), Suit.Spades(), Color.Red())

  /**
   * Seven cards.
   */
  val SEVEN_HEARTS: Card = Card(Rank.Seven(), Suit.Hearts(), Color.Red())
  val SEVEN_CLUBS: Card = Card(Rank.Seven(), Suit.Clubs(), Color.Red())
  val SEVEN_SPADES: Card = Card(Rank.Seven(), Suit.Spades(), Color.Red())

  /**
   * Eight cards.
   */
  val EIGHT_CLUBS: Card = Card(Rank.Eight(), Suit.Clubs(), Color.Red())
  val EIGHT_SPADES: Card = Card(Rank.Eight(), Suit.Spades(), Color.Red())

  /**
   * Nine cards.
   */
  val NINE_HEARTS: Card = Card(Rank.Nine(), Suit.Hearts(), Color.Red())
  val NINE_CLUBS: Card = Card(Rank.Nine(), Suit.Clubs(), Color.Red())
  val NINE_SPADES: Card = Card(Rank.Nine(), Suit.Spades(), Color.Red())

  /**
   * Ten cards.
   */
  val TEN_CLUBS: Card = Card(Rank.Ten(), Suit.Clubs(), Color.Blue())
  val TEN_SPADES: Card = Card(Rank.Ten(), Suit.Spades(), Color.Blue())
  val TEN_HEARTS: Card = Card(Rank.Ten(), Suit.Hearts(), Color.Red())
  val TEN_DIAMONDS: Card = Card(Rank.Ten(), Suit.Diamonds(), Color.Red())

  /**
   * Jack cards.
   */
  val JACK_HEARTS: Card = Card(Rank.Jack(), Suit.Hearts(), Color.Blue())
  val JACK_CLUBS: Card = Card(Rank.Jack(), Suit.Clubs(), Color.Blue())
  val JACK_SPADES: Card = Card(Rank.Jack(), Suit.Spades(), Color.Blue())

  /**
   * Queen cards.
   */
  val QUEEN_HEARTS: Card = Card(Rank.Queen(), Suit.Hearts(), Color.Red())
  val QUEEN_CLUBS: Card = Card(Rank.Queen(), Suit.Clubs(), Color.Blue())
  val QUEEN_DIAMONDS: Card = Card(Rank.Queen(), Suit.Diamonds(), Color.Blue())
  val QUEEN_SPADES: Card = Card(Rank.Queen(), Suit.Spades(), Color.Blue())

  /**
   * King cards.
   */
  val KING_DIAMONDS: Card = Card(Rank.King(), Suit.Diamonds(), Color.Red())
  val KING_HEARTS: Card = Card(Rank.King(), Suit.Hearts(), Color.Red())
  val KING_SPADES: Card = Card(Rank.King(), Suit.Spades(), Color.Blue())
  val KING_CLUBS: Card = Card(Rank.King(), Suit.Clubs(), Color.Blue())
}
