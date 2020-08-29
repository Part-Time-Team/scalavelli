package it.parttimeteam.core.cards

import it.parttimeteam.core.cards
import it.parttimeteam.core.cards.Rank.{Ace, Eight, Five, Four, Jack, King, Nine, Queen, Seven, Six, Ten, Three, Two}
import it.parttimeteam.core.cards.Suit.{Clubs, Diamonds, Hearts, Spades}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

class CardSuite extends AnyFunSuite {
  test("Naming and ShortNaming") {
    val card: Card = cards.Card(Rank.Ace(), Suit.Spades(), Color.Red())
    assert(card.name equals f"${Rank.Ace().name} of ${Suit.Spades().name} of ${Color.Red().name}")
    assert(card.shortName equals f"${Rank.Ace().shortName}${Suit.Spades().shortName}${Color.Red().shortName}")
  }

  test("Try isNext on different Suit") {
    val h2: Card = cards.Card(Rank.Two(), Suit.Hearts(), Color.Red())
    val s1: Card = cards.Card(Rank.Ace(), Suit.Spades(), Color.Red())
    assert(!(h2 isNext s1))
  }

  test("Try isNext on same Suit") {
    val s2: Card = cards.Card(Rank.Two(), Suit.Spades(), Color.Red())
    val s1: Card = cards.Card(Rank.Ace(), Suit.Spades(), Color.Blue())
    assert(s2 isNext s1)
  }

  test("Try isNext on Ace and King of the Suit") {
    val s1: Card = cards.Card(Rank.Ace(), Suit.Spades(), Color.Red())
    val sk: Card = cards.Card(Rank.King(), Suit.Spades(), Color.Red())
    assert(s1 isNext sk)
  }

  test("Try string2card from correct cards") {
    val h3: Card = cards.Card(Rank.Three(), Suit.Hearts(), Color.Red())
    assert(Card string2card h3.shortName equals h3)
  }

  test("Invoking toString on a card will produce the shortname") {
    val c4: Card = cards.Card(Rank.Four(), Suit.Clubs(), Color.Red())
    assert(c4.toString equals c4.shortName)
  }

  test("Invoking string2card on currect value will produce RuntimeException") {
    val s: String = "g1"
    assertThrows[RuntimeException] {
      Card string2card s
    }
  }
}