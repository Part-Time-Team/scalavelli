package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Prolog, Term, Var}
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import it.parttimeteam.core.prolog.engine.PrologGameEngine
import org.scalatest.funsuite.AnyFunSuite

class PrologGameConverterSuite extends AnyFunSuite {

  val prologConverter: PrologGameConverter = new PrologGameConverter
  val prolog: Prolog = new Prolog

  test("Convert sequence of cards in string") {

    val card1: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Red())
    val card2: Card = Card(Rank.Four(), Suit.Spades(), Color.Blue())
    val card3: Card = Card(Rank.King(), Suit.Diamonds(), Color.Red())

    assertResult("([(1,\"Clubs\"),(4,\"Spades\"),(13,\"Diamonds\")]).")(prologConverter.cardsConvertToString(Seq(card1, card2, card3))(None))
    assertResult("([(1,\"Clubs\"),(4,\"Spades\"),(13,\"Diamonds\")],X).")(prologConverter.cardsConvertToString(Seq(card1, card2, card3))(Some(new Var("X"))))
  }

  test("Convert term to string and replace specific characters") {

    val term: Term = prolog toTerm "['1']"
    val seq: Term = prolog toTerm "([(1,\"Clubs\"),(1,\"Spades\")])"

    assertResult("[1]")(prologConverter toString(term, "'"))
    assertResult("[(1,'Clubs'),(1,'Spades')]")(prologConverter toString(seq, "','"))
  }

  test("Convert a sequence of term into a tuple sequence"){

    val prologEngine = new PrologGameEngine
    val card1: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Blue())
    val card2: Card = Card(Rank.Ace(), Suit.Spades(), Color.Blue())
    val card3: Card = Card(Rank.Ace(), Suit.Diamonds(), Color.Red())
    val termSeq :Seq[Term] = prologEngine goal "quicksortValue" + prologConverter.cardsConvertToString(Seq(card1, card2, card3))(Some(new Var("X")))
    val tupleSeq :Seq[(Rank, Suit)] = Seq(card1, card2, card3) map(card => (card.rank, card.suit))

    assertResult(tupleSeq)(prologConverter.sortedCard(termSeq))
  }

  test("Get a card given the color, rank and suit"){
    val card: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Blue())

    assertResult(card)(prologConverter getCard(prolog toTerm "B", prolog toTerm "Clubs",  prolog toTerm "1"))
  }

  test("Change optional value of the card Ace if it is after King card"){

    val QUEEN_CLUBS: Card = Card(Rank.Queen(), Suit.Clubs(), Color.Red())
    val KING_CLUBS: Card = Card(Rank.King(), Suit.Clubs(), Color.Blue())
    val ACE_CLUBS: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Red())
    val TWO_CLUBS: Card = Card(Rank.Two(), Suit.Clubs(), Color.Blue())
    val THREE_CLUBS: Card = Card(Rank.Three(), Suit.Clubs(), Color.Red())

    assertResult(Seq(QUEEN_CLUBS, KING_CLUBS, ACE_CLUBS.copy(rank = "14")))(prologConverter.optionalValueCards(Seq(QUEEN_CLUBS, KING_CLUBS, ACE_CLUBS)))
    assertResult(Seq(QUEEN_CLUBS, KING_CLUBS, ACE_CLUBS.copy(rank = "14"), TWO_CLUBS, THREE_CLUBS))(prologConverter.optionalValueCards(Seq(QUEEN_CLUBS, KING_CLUBS, ACE_CLUBS, TWO_CLUBS, THREE_CLUBS)))
    assertResult(Seq(ACE_CLUBS, TWO_CLUBS, THREE_CLUBS))(prologConverter.optionalValueCards(Seq(ACE_CLUBS, TWO_CLUBS, THREE_CLUBS)))
  }

  test("Convert a tuple sequence into string"){

    val QUEEN_CLUBS: Card = Card(Rank.Queen(), Suit.Clubs(), Color.Red())
    val KING_CLUBS: Card = Card(Rank.King(), Suit.Clubs(), Color.Blue())
    val tupleSeq: Seq[(Int, String)] = Seq(QUEEN_CLUBS, KING_CLUBS) map(card => (card.rank.value, card.suit.name))
    val varX = new Var("X")

    assertResult("(["+tupleSeq.mkString(",")+"]).")(prologConverter.prologList(tupleSeq)(None))
    assertResult("(["+tupleSeq.mkString(",")+"],"+ varX.getName+ ").")(prologConverter.prologList(tupleSeq)(Some(varX)))
  }
}
