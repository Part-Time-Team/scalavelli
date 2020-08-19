package it.parttimeteam.core.collections

import org.scalatest.funspec.AnyFunSpec

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

    describe("After draw") {
      it("Must have less card than before") {
        val deck: Deck = Deck.sorted
        val before = deck.remaining
        assert(before equals 104)
        val cardDrawn = deck.draw()
        assert(cardDrawn._1.remaining equals 103)
        assert(!(cardDrawn._2.name equals ""))
      }

      it("Must be empty if all cards are drawn") {
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
}
