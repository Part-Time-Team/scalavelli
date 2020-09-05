package it.parttimeteam.core.prolog

import it.parttimeteam.core.cards.Color.{Blue, Red}
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import it.parttimeteam.core.cards.Rank.{Ace, Eight, Five, Four, Jack, King, Nine, Queen, Seven, Six, Ten, Three, Two}
import it.parttimeteam.core.cards.Suit.{Clubs, Diamonds, Hearts, Spades}
import it.parttimeteam.core.TestCards._
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
    Seq(ACE_CLUBS, ACE_DIAMONDS, ACE_SPADES),
    Seq(TEN_CLUBS, TEN_HEARTS, TEN_SPADES, TEN_DIAMONDS)
  )

  /**
   * Invalid Quarter
   */
  val invalidQuarter: TableFor1[Seq[Card]] = Table(
    "invalid quarter",
    Seq(ACE_SPADES, ACE_DIAMONDS, TEN_CLUBS),
    Seq(TEN_CLUBS, TEN_CLUBS, TEN_SPADES)
  )

  /**
   * Valid Chain
   */
  val validChain: TableFor1[Seq[Card]] = Table(
    "valid chain",
    Seq(ACE_CLUBS, TWO_CLUBS, THREE_CLUBS),
    Seq(JACK_HEARTS, QUEEN_HEARTS, KING_HEARTS, ACE_HEARTS)
  )

  /**
   * Invalid Chain
   */
  val invalidChain: TableFor1[Seq[Card]] = Table(
    "invalid chain",
    Seq(ACE_CLUBS, TWO_CLUBS, THREE_CLUBS, FOUR_SPADES),
    Seq(ACE_CLUBS, THREE_CLUBS, FOUR_SPADES),
    Seq(JACK_HEARTS, QUEEN_HEARTS, KING_HEARTS, ACE_HEARTS, TWO_HEARTS)
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
