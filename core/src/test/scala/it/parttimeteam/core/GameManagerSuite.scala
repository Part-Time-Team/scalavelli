package it.parttimeteam.core

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{CardCombination, Deck, Hand}
import it.parttimeteam.{Board, GameState}
import org.scalatest.funspec.AnyFunSpec

class GameManagerSuite extends AnyFunSpec {
  describe("A game manager") {
    val gameManager: GameManager = new GameManagerImpl()
    val ids = Seq("Daniele", "Lorenzo", "Luca", "Matteo")

    describe("Can create a game") {
      it("Empty if no players are added") {
        assert(gameManager.create(Seq.empty) equals
          GameState(List.empty, Deck.empty, Board.EmptyBoard()))
      }

      it("Filled if players are added") {
        // TODO: Need to create a stub game state because the creation
        // generate a game state with a shuffled deck.
        assert(gameManager.create(ids) equals
          GameState(List.empty, Deck.sorted, Board.EmptyBoard()))
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
      it("With complex op") {
        pending
      }
    }

    describe("Validate a combination") {
      // TODO: Make a test for each possible combination.
      describe("Valid combinations") {
        it("Tris") {
          pending
        }

        it("Poker") {
          pending
        }

        it("Chair") {
          pending
        }
      }

      describe("Don't validate a invalid combination") {
        it("Tris with same suit cards") {
          pending
        }

        it("Chair with same rank cards") {
          pending
        }
      }
    }

    describe("Play a combination") {
      val c1 = Card.string2card("2♣")
      val c2 = Card.string2card("3♣")
      val c3 = Card.string2card("4♣")
      val comb = CardCombination(List(c1, c2, c3))
      val state = gameManager.create(ids)
      val played = gameManager.playCombination(Hand(), state.board, comb)
      assert(played._1.tableCards contains c1)
      assert(!(played._1.tableCards contains c1))
    }

    describe("Pick table cards") {
      it("Pick from table some cards") {
        val c1 = Card.string2card("2♣")
        val c2 = Card.string2card("3♣")
        val c3 = Card.string2card("4♣")
        val comb = CardCombination(List(c1, c2, c3))
        var state = gameManager.create(ids)
        var daniele = state > "Daniele"
        val played = gameManager.playCombination(
          daniele.getHand, state.board, comb)
        state.board = played._2
        daniele = daniele.setHand(played._1)
        state = state < daniele
        val picked =
          gameManager.pickTableCards(
            (state > "Daniele").getHand,
            state.board,
            c1)
        assert(picked._1.tableCards contains c1)
        assert(!(picked._1.tableCards contains c1))
      }
    }
  }
}
