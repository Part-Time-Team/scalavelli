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
    Seq(Cards().ACE_CLUBS, Cards().ACE_DIAMONDS, Cards().ACE_SPADES),
    Seq(Cards().TEN_CLUBS, Cards().TEN_HEARTS, Cards().TEN_SPADES, Cards().TEN_DIAMONDS)
  )

  /**
   * Invalid Quarter
   */
  val invalidQuarter: TableFor1[Seq[Card]] = Table(
    "invalid quarter",
    Seq(Cards().ACE_SPADES, Cards().ACE_DIAMONDS, Cards().TEN_CLUBS),
    Seq(Cards().TEN_CLUBS, Cards().TEN_CLUBS, Cards().TEN_SPADES)
  )

  /**
   * Valid Chain
   */
  val validChain: TableFor1[Seq[Card]] = Table(
    "valid chain",
    Seq(Cards().ACE_CLUBS, Cards().TWO_CLUBS, Cards().THREE_CLUBS),
    Seq(Cards().JACK_HEARTS, Cards().QUEEN_HEARTS, Cards().KING_HEARTS, Cards().ACE_HEARTS)
  )

  /**
   * Invalid Chain
   */
  val invalidChain: TableFor1[Seq[Card]] = Table(
    "invalid chain",
    Seq(Cards().ACE_CLUBS, Cards().TWO_CLUBS, Cards().THREE_CLUBS, Cards().FOUR_SPADES),
    Seq(Cards().ACE_CLUBS, Cards().THREE_CLUBS, Cards().FOUR_SPADES),
    Seq(Cards().JACK_HEARTS, Cards().QUEEN_HEARTS, Cards().KING_HEARTS, Cards().ACE_HEARTS, Cards().TWO_HEARTS)
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
