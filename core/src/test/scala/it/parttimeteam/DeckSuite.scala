package it.parttimeteam

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.funsuite.AnyFunSuite

class DeckSuite extends AnyFunSuite {
  test("Drawing a card return a deck with less cards") {
    val deck: Deck = Deck.sorted
    val before = deck.remaining
    assert(before equals 52)
    val card = deck.draw()
    assert(!(card.name() equals ""))
    assert(deck.remaining equals 51)
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
      val deckToEmpty = Deck.shuffled
      assert(!deckToEmpty.isEmpty)
      // For each card, draw a card.
      for {_ <- Deck.sorted.cards} deckToEmpty.draw()
      assert(deckToEmpty.isEmpty)
    }
  }
}
