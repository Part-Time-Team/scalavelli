package it.parttimeteam.core.cards

import it.parttimeteam.core.cards
import org.scalatest.funsuite.AnyFunSuite

class CardSuite extends AnyFunSuite {
  val s1r: Card = cards.Card(Rank.Ace(), Suit.Spades(), Color.Red())
  val s2r: Card = cards.Card(Rank.Two(), Suit.Spades(), Color.Red())
  val skr: Card = cards.Card(Rank.King(), Suit.Spades(), Color.Red())
  val h2r: Card = cards.Card(Rank.Two(), Suit.Hearts(), Color.Red())
  val h3r: Card = cards.Card(Rank.Three(), Suit.Hearts(), Color.Red())
  val c4r: Card = cards.Card(Rank.Four(), Suit.Clubs(), Color.Red())
  test("Naming and ShortNaming") {
    assert(s1r.name equals f"${Rank.Ace().name} of ${Suit.Spades().name} of ${Color.Red().name}")
    assert(s1r.shortName equals f"${Rank.Ace().shortName}${Suit.Spades().shortName}${Color.Red().shortName}")
  }

  test("Try isNext on different Suit") {
    assert(!(h2r isNext s1r))
  }

  test("Try isNext on same Suit") {
    assert(s2r isNext s1r)
  }

  test("Try isNext on Ace and King of the Suit") {
    assert(s1r isNext skr)
  }

  test("Try string2card from correct cards") {
    assert(Card string2card h3r.shortName equals h3r)
  }

  test("Invoking toString on a card will produce the shortname") {
    assert(c4r.toString equals c4r.shortName)
  }

  test("Invoking string2card on currect value will produce RuntimeException") {
    val s: String = "g1"
    assertThrows[RuntimeException] {
      Card string2card s
    }
  }

  test("A sequence of cards can be ordered by Rank or Suit") {
    val s = Seq(c4r, s2r, h3r)
    assertResult(Seq(s2r, h3r, c4r))(s.sortByRank())
    assertResult(Seq(h3r, c4r, s2r))(s.sortBySuit())
  }
}
