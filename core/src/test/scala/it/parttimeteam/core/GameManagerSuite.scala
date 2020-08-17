package it.parttimeteam.core

import it.parttimeteam.{Board, GameState}
import it.parttimeteam.core.collections.Deck
import org.scalatest.funspec.AnyFunSpec

class GameManagerSuite extends AnyFunSpec {
  describe("A game manager") {
    val gameManager: GameManager = new GameManagerImpl()
    describe("Can create a game") {
      it("Empty if no players are added") {
        val ids = Seq.empty
        assert(gameManager.create(ids) equals GameState(List.empty, Deck.empty, Board.EmptyBoard()))
      }

      it("Filled if players are added") {
        // TODO: Need to create a stub game state because the creation
        // generate a game state with a shuffled deck.
        val ids = Seq("Daniele", "Lorenzo", "Luca", "Matteo")
        assert(gameManager.create(ids) equals GameState(List.empty, Deck.sorted, Board.EmptyBoard()))
      }
    }

    describe("Draw cards from a deck") {
      val deck = Deck.shuffled
      it("Draw cards from a non empty deck") {
        val drawedDeck = Deck.cards2deck(deck.cards.tail)
        assert(gameManager.draw(deck) equals (drawedDeck, deck.cards.head))
      }

      it("Thwow an exception when drawing from an empty deck") {
        // TODO: Implement a test that catch an exception.
      }
    }

    describe("Validate a turn") {

    }

    describe("Validate a combination") {

    }

    describe("Pick table cards") {

    }

    describe("Play a combination") {

    }
  }
}
