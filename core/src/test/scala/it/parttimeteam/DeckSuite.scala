package it.parttimeteam

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.funsuite.AnyFunSuite

class DeckSuite extends AnyFunSuite {
  /*test ("Take the deck sorted") {
    val deck: Deck = Deck.sorted
    for{card <- deck draw}
  }

  test("Draw the first card of the deck") {

  }*/
  // Shuffle
  // Pesca carta
  // isEmpty
}

class DeckSpec extends AnyFunSpec {
  describe ("A deck") {
    it("Must be empty if inizialed as is") {
      assert(Deck.empty.isEmpty)
    }

    it("must be not empty at creation") {
      assert(!Deck.shuffled.isEmpty)
    }

    it("should be empty after some draws") {
      val deckToEmpty = Deck.shuffled
      @scala.annotation.tailrec
      def drawAllCards(deck: Deck): Deck = deck match {
        case deck if deck.remaining == 0 => deck
        case _ =>
          deck.draw()
          drawAllCards(deck)
      }
      drawAllCards(deckToEmpty)
      assert(deckToEmpty.isEmpty)
    }
  }
}
