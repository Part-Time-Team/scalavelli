package it.parttimeteam.core.prolog.engine

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.prolog.Cards
import it.parttimeteam.core.prolog.converter.PrologGameConverter
import org.scalatest.matchers.should
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

class PrologGameEnginePropSpec extends AnyPropSpec with TableDrivenPropertyChecks with should.Matchers  {


  val VALIDATION_QUARTER = "validationQuarter"

  val prologEngine: PrologGameEngine = new PrologGameEngine
  val conversion : PrologGameConverter = new PrologGameConverter()

  val sequenceCards: TableFor1[Seq[Card]] = Table(
    "sequence cards",
    Seq(Cards().ACE_CLUBS, Cards().ACE_DIAMONDS, Cards().ACE_SPADES),
    Seq(Cards().TEN_CLUBS, Cards().TEN_HEARTS, Cards().TEN_SPADES, Cards().TEN_DIAMONDS)
  )

  property("Check if the predicate has solution"){
    forAll(sequenceCards){ cards =>
      prologEngine isSuccess VALIDATION_QUARTER + conversion.cardsConvertToString(cards)(None) should be (true)
    }
  }
}
