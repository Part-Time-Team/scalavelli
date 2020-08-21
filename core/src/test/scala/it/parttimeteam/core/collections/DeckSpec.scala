package it.parttimeteam.core.collections

import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import org.scalatest.funspec.AnyFunSpec

class DeckSpec extends AnyFunSpec {
  describe("A deck") {
    describe("At inizialization") {
      it("Must be empty") {
        assert(Deck.empty.isEmpty)
      }

      it("Must or not empty is shuffled or sorted") {
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

      it("Must be empty drawing all cards") {
        val deckToEmpty = Deck.cards2deck(
          List(
            Card(Rank.Ace(), Suit.Clubs(), Color.Blue()),
            Card(Rank.King(), Suit.Clubs(), Color.Blue()),
            Card(Rank.Jack(), Suit.Clubs(), Color.Blue())
          ))

        assert(!deckToEmpty.isEmpty)
        val cardToDraw = 2 // deckToEmpty.cards.size
        val deckDrawn = deckToEmpty.draw(cardToDraw)
        assertResult(cardToDraw)(deckDrawn._2.size) // Card drawn.
        assertResult(deckToEmpty.cards.size - cardToDraw)(deckDrawn._1.cards.size) // Remaining deck.
        // assert(deckDrawn._1.isEmpty)
      }
    }
  }
}
