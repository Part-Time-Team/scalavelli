package it.parttimeteam.core.collections

import it.parttimeteam.core.TestCards._
import it.parttimeteam.core.cards.Card
import org.scalatest.matchers.should
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor2}
import org.scalatest.propspec.AnyPropSpec

class HandPropSpec
  extends AnyPropSpec
    with TableDrivenPropertyChecks
    with should.Matchers {
  val toOrderByRank: TableFor2[Seq[Card], Seq[Card]] = Table(
    ("playerCards", "tableCards"),
    (Seq(TWO_CLUBS, FOUR_CLUBS, ACE_CLUBS, THREE_CLUBS), Seq(SIX_HEARTS, THREE_HEARTS, FIVE_HEARTS, FOUR_HEARTS)),
    (Seq(THREE_CLUBS, FOUR_CLUBS, TWO_CLUBS, ACE_CLUBS), Seq(THREE_HEARTS, FIVE_HEARTS, FOUR_HEARTS, SIX_HEARTS)),
  )

  val toOrderBySuit: TableFor2[Seq[Card], Seq[Card]] = Table(
    ("playerCards", "tableCards"),
    (Seq(ACE_SPADES, ACE_HEARTS, ACE_CLUBS, ACE_DIAMONDS), Seq(TWO_SPADES, TWO_CLUBS, TWO_HEARTS))
  )

  property("Check sorting by Rank with different cases.") {
    forAll(toOrderByRank) { (playerCards, tableCards) =>
      val hand = Hand(playerCards, tableCards)
      hand sortByRank() should be(Hand(playerCards sortByRank(), tableCards sortByRank()))
    }
  }

  property("Check sorting by Suit with different cases.") {
    forAll(toOrderBySuit) { (playerCards, tableCards) =>
      val hand = Hand(playerCards, tableCards)
      hand sortBySuit() should be(Hand(playerCards sortBySuit(), tableCards sortBySuit()))
    }
  }
}
