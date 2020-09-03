package it.parttimeteam.core.prolog

import it.parttimeteam.core.cards.Color.{Blue, Red}
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import it.parttimeteam.core.cards.Rank.{Ace, Eight, Five, Four, Jack, King, Nine, Queen, Seven, Six, Ten, Three, Two}
import it.parttimeteam.core.cards.Suit.{Clubs, Diamonds, Hearts, Spades}
import org.scalatest.matchers.should
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

class PrologGamePropSpec extends AnyPropSpec with TableDrivenPropertyChecks with should.Matchers {

  val prologGame = new PrologGame

  /**
   * Rank
   */
  val rank: TableFor1[Rank] = Table(
    "rank",
    Ace(),
    Two(),
    Three(),
    Four(),
    Five(),
    Six(),
    Seven(),
    Eight(),
    Nine(),
    Ten(),
    Jack(),
    Queen(),
    King()
  )

  /**
   * Suit
   */
  val suit: TableFor1[Suit] = Table(
    "suit",
    Clubs(),
    Hearts(),
    Diamonds(),
    Spades()
  )

  /**
   * Color
   */
  val color: TableFor1[Color] = Table(
    "color",
    Blue(),
    Red()
  )

  /**
   * Valid Quarter
   */
  val validQuarter: TableFor1[Seq[Card]] = Table(
    "valid quarter",
    Seq(TestCards.ACE_CLUBS, TestCards.ACE_DIAMONDS, TestCards.ACE_SPADES),
    Seq(TestCards.TEN_CLUBS, TestCards.TEN_HEARTS, TestCards.TEN_SPADES, TestCards.TEN_DIAMONDS)
  )

  /**
   * Invalid Quarter
   */
  val invalidQuarter: TableFor1[Seq[Card]] = Table(
    "invalid quarter",
    Seq(TestCards.ACE_SPADES, TestCards.ACE_DIAMONDS, TestCards.TEN_CLUBS),
    Seq(TestCards.TEN_CLUBS, TestCards.TEN_CLUBS, TestCards.TEN_SPADES)
  )

  /**
   * Valid Chain
   */
  val validChain: TableFor1[Seq[Card]] = Table(
    "valid chain",
    Seq(TestCards.ACE_CLUBS, TestCards.TWO_CLUBS, TestCards.THREE_CLUBS),
    Seq(TestCards.JACK_HEARTS, TestCards.QUEEN_HEARTS, TestCards.KING_HEARTS, TestCards.ACE_HEARTS)
  )

  /**
   * Invalid Chain
   */
  val invalidChain: TableFor1[Seq[Card]] = Table(
    "invalid chain",
    Seq(TestCards.ACE_CLUBS, TestCards.TWO_CLUBS, TestCards.THREE_CLUBS, TestCards.FOUR_SPADES),
    Seq(TestCards.ACE_CLUBS, TestCards.THREE_CLUBS, TestCards.FOUR_SPADES),
    Seq(TestCards.JACK_HEARTS, TestCards.QUEEN_HEARTS, TestCards.KING_HEARTS, TestCards.ACE_HEARTS, TestCards.TWO_HEARTS)
  )

  property("loadDeck must return any possible card in the deck") {
    forAll(rank) { rank =>
      forAll(suit) {
        suit =>
          forAll(color) {
            color =>
              rank.shortName + suit.shortName + color.shortName contains prologGame.loadDeck
          }
      }
    }
  }

  property("Validate a valid quarter") {
    forAll(validQuarter) { quarter =>
      prologGame.validateQuarter(quarter) should be(true)
    }
  }

  property("Validate an invalid quarter") {
    forAll(invalidQuarter) { quarter =>
      prologGame.validateQuarter(quarter) should be(false)
    }
  }

  property("Validate a valid chain") {
    forAll(validChain) { chain =>
      prologGame.validateChain(chain) should be(true)
    }
  }

  property("Validate an invalid chain") {
    forAll(invalidChain) { chain =>
      prologGame.validateChain(chain) should be(false)

    }
  }
}
