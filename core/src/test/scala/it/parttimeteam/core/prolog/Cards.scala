package it.parttimeteam.core.prolog

import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}

/**
 * Case class cards test
 */
case class Cards() {

  /**
   * Ace cards
   */
  val ACE_CLUBS: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Blue())
  val ACE_SPADES: Card = Card(Rank.Ace(), Suit.Spades(), Color.Blue())
  val ACE_DIAMONDS: Card = Card(Rank.Ace(), Suit.Diamonds(), Color.Red())
  val ACE_HEARTS: Card = Card(Rank.Ace(), Suit.Hearts(), Color.Red())

  /**
   * Two cards
   */
  val TWO_CLUBS: Card = Card(Rank.Two(), Suit.Clubs(), Color.Blue())
  val TWO_HEARTS: Card = Card(Rank.Two(), Suit.Hearts(), Color.Red())

  /**
   * Three cards
   */
  val THREE_CLUBS: Card = Card(Rank.Three(), Suit.Clubs(), Color.Blue())

  /**
   * Four cards
   */
  val FOUR_SPADES: Card = Card(Rank.Four(), Suit.Spades(), Color.Blue())

  /**
   * Five cards
   */
  val FIVE_HEARTS: Card = Card(Rank.Five(), Suit.Hearts(), Color.Red())

  /**
   * Six cards
   */
  val SIX_HEARTS: Card = Card(Rank.Six(), Suit.Hearts(), Color.Red())

  /**
   * Seven cards
   */
  val SEVEN_HEARTS: Card = Card(Rank.Seven(), Suit.Hearts(), Color.Red())

  /**
   * Ten cards
   */
  val TEN_CLUBS: Card = Card(Rank.Ten(), Suit.Clubs(), Color.Blue())
  val TEN_SPADES: Card = Card(Rank.Ten(), Suit.Spades(), Color.Blue())
  val TEN_HEARTS: Card = Card(Rank.Ten(), Suit.Hearts(), Color.Red())
  val TEN_DIAMONDS: Card = Card(Rank.Ten(), Suit.Diamonds(), Color.Red())

  /**
   * Jack cards
   */
  val JACK_HEARTS: Card = Card(Rank.Jack(), Suit.Hearts(), Color.Blue())

  /**
   * Queen cards
   */
  val QUEEN_HEARTS: Card = Card(Rank.Queen(), Suit.Hearts(), Color.Red())
  val QUEEN_CLUBS: Card = Card(Rank.Queen(), Suit.Clubs(), Color.Blue())

  /**
   * King cards
   */
  val KING_HEARTS: Card = Card(Rank.King(), Suit.Hearts(), Color.Red())
  val KING_SPADES: Card = Card(Rank.King(), Suit.Spades(), Color.Blue())
}
