package it.parttimeteam

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.funsuite.AnyFunSuite

class DeckSuite extends AnyFunSuite {
  test("Drawing a card return a deck with less cards") {
    val deck: Deck = Deck.sorted
    val before = deck.remaining
    assert(before equals 52)
    val cardDrawn = deck.draw()
    assert(cardDrawn._1.remaining equals 51)
    assert(!(cardDrawn._2.name() equals ""))
  }

  /*test("Draw the first card of the deck") {

  }*/
  // Shuffle
  // Pesca carta
  // isEmpty
}

class DeckSpec extends AnyFunSpec {
  describe("A deck") {
    describe("At inizialization") {
      it("Must be empty") {
        assert(Deck.empty.isEmpty)
      }

      it("must or not empty is shuffled or sorted") {
        assert(!Deck.shuffled.isEmpty)
        assert(!Deck.sorted.isEmpty)
      }
    }

    it("should be empty after some draws") {
      var deckToEmpty = Deck.shuffled
      assert(!deckToEmpty.isEmpty)
      // For each card, draw a card.
      for {_ <- Deck.sorted.cards} {
        val cardDrawn = deckToEmpty.draw()
        deckToEmpty = cardDrawn._1
      }
      assert(deckToEmpty.isEmpty)
    }
  }
}
