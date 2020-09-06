package it.parttimeteam.core.cards

import it.parttimeteam.core.TestCards._
import org.scalatest.funsuite.AnyFunSuite

class CardSuite extends AnyFunSuite {

  test("Naming and ShortNaming") {
    assert(ACE_SPADES.name equals f"${Rank.Ace().name} of ${Suit.Spades().name} of ${Color.Blue().name}")
    assert(ACE_SPADES.shortName equals f"${Rank.Ace().shortName}${Suit.Spades().shortName}${Color.Blue().shortName}")
  }

  test("Try isNext on different Suit") {
    assert(!(TWO_HEARTS isNext ACE_SPADES))
  }

  test("Try isNext on same Suit") {
    assert(TWO_SPADES isNext ACE_SPADES)
  }

  test("Try isNext on Ace and King of the Suit") {
    assert(ACE_SPADES isNext KING_SPADES)
  }

  test("Try string2card from correct cards") {
    assert(Card string2card THREE_HEARTS.shortName equals THREE_HEARTS)
  }

  test("Invoking toString on a card will produce the shortname") {
    assert(FOUR_CLUBS.toString equals FOUR_CLUBS.shortName)
  }

  test("Invoking string2card on currect value will produce RuntimeException") {
    val s: String = "g1"
    assertThrows[RuntimeException] {
      Card string2card s
    }
  }

  test("A sequence of cards can be ordered by Rank or Suit") {
    val s = Seq(FOUR_CLUBS, TWO_SPADES, THREE_HEARTS)
    assertResult(Seq(TWO_SPADES, THREE_HEARTS, FOUR_CLUBS))(s.sortByRank())
    assertResult(Seq(THREE_HEARTS, FOUR_CLUBS, TWO_SPADES))(s.sortBySuit())
  }
}
