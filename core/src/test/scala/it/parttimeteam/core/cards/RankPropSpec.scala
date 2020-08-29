package it.parttimeteam.core.cards

import it.parttimeteam.core.cards.Rank.{Ace, Eight, Five, Four, Jack, King, Nine, Queen, Seven, Six, Ten, Three, Two}
import org.scalatest.matchers.should
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

class RankPropSpec
  extends AnyPropSpec
    with TableDrivenPropertyChecks
    with should.Matchers {
  val examples: TableFor1[Rank] = Table(
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

  val failures: TableFor1[String] = Table(
    "rank",
    "s",
    "♣",
    "♠",
    "♦",
    "♥"
  )

  property("string2rank must return shortName at name input") {
    forAll(examples) { rank =>
      Rank string2rank rank.shortName should be(rank)
      Rank string2rank rank.shortName equals rank should be(true)
    }
  }

  property("Invoking string2rank with other strings must return RuntimeException") {
    forAll(failures) { fail =>
      a[RuntimeException] should be thrownBy {
        Rank string2rank fail
      }
    }
  }

  property("Check comparation between suits") {
    forAll(examples) { rank =>
      rank compareTo Six() should be(rank.value compareTo Six().value)
    }
  }

  property("value2rank must return Rank at value input") {
    forAll(examples) { rank =>
      Rank value2rank rank.value should be(rank)
    }
  }

  property("equals must return boolean values") {
    forAll(examples) { rank =>
      rank equals rank should be(true)
    }
  }

  val int2fails: TableFor1[Int] = Table(
    "rank",
    -1,
    14,
  )

  property("Invoking value2rank with other values must return RuntimeException") {
    forAll(int2fails) { fail =>
      a[RuntimeException] should be thrownBy {
        Rank value2rank fail
      }
    }
  }
}
