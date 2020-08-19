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

      it("Tostring must return a compatible string") {
        val simpleDeck = Deck(Card.string2card(s"${Rank.Ace().shortName}${Suit.Clubs().shortName}${Color.Blue().shortName}") :: Nil)
        val str = simpleDeck.toString
        println(str)
        assert(str equals "A♣B")
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
