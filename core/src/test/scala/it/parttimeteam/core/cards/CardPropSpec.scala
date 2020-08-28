package it.parttimeteam.core.cards

import it.parttimeteam.core.cards.Rank.{Ace, Eight, Five, Four, Jack, King, Nine, Queen, Seven, Six, Ten, Three, Two}
import it.parttimeteam.core.cards.Suit.{Clubs, Diamonds, Hearts, Spades}
import org.scalatest.matchers.should
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

class CardPropSpec
  extends AnyPropSpec
    with TableDrivenPropertyChecks
    with should.Matchers {

  val ranks: TableFor1[Rank] = Table(
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

  val suits: TableFor1[Suit] = Table(
    "suit",
    Spades(),
    Diamonds(),
    Hearts(),
    Clubs()
  )

  val tester: Card = Card(Rank.Three(), Suit.Spades(), Color.Red())

  property("Check card compareto to another card with same suit.") {
    forAll(ranks) { rank =>
      val card = Card(rank, Suit.Spades(), Color.Red())
      card compareTo tester should be(card.rank compareTo rank)
    }
  }

  property("Check card compareto to another card with different suit") {
    forAll(suits) { suit =>
      val card = Card(Rank.Five(), suit, Color.Blue())
      card compareTo tester should be(card.suit compareTo suit)
    }
  }
}
