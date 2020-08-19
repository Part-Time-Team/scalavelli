package it.parttimeteam.core

import it.parttimeteam.Board
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{CardCombination, Deck, Hand}
import it.parttimeteam.core.player.Player
import org.scalamock.matchers.Matchers
import org.scalamock.scalatest.MockFactory
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.an

class GameManagerSuite extends AnyFunSpec with MockFactory with Matchers {
  describe("A game manager") {
    val gameManager: GameManager = new GameManagerImpl()
    val ids = Seq("Daniele", "Lorenzo", "Luca", "Matteo")

    describe("Can create a game") {
      it("Empty if no players are added") {
        assert(gameManager.create(Seq.empty).players equals List.empty)
      }

      it("Filled if players are added") {
        // TODO: Need to create a stub game state because the creation
        // generate a game state with a shuffled deck.
        val list = ids.map(i => Player("", i, Hand(Nil, Nil)))
        assert(gameManager.create(ids).players equals list)
      }
    }

    describe("Draw cards from a deck") {
      val deck = Deck.shuffled
      it("Draw cards from a non empty deck") {
        val drawedDeck = Deck.cards2deck(deck.cards.tail)
        assert(gameManager.draw(deck) equals(drawedDeck, deck.cards.head))
      }

      it("Thwow an exception when drawing from an empty deck") {
        // TODO: Implement a test that catch an exception.
        val emptyDeck = Deck.empty
        an[UnsupportedOperationException] should be thrownBy
          (gameManager draw emptyDeck)
        pending
      }
    }

    describe("Validate a turn") {
      val stubManager = stub[GameManager]
      // If the board is empty and the hand too, return a valid turn.
      stubManager.validateTurn _ when(Board(Nil), Hand(Nil, Nil)) returns true

      // If the board is filled with a combination and the hand is empty, return a valid turn
      stubManager.validateTurn _ when(Board(List(CardCombination(Card.string2card("2♣") :: Nil))), Hand(Nil, Nil)) returns true
      // TODO: Waiting for PROLOG part.
      it("With complex op") {
        // assert(stubManager.validateTurn(Board(Nil), Hand(Nil, Nil)) equals true)
        // assert(stubManager.validateTurn(Board(List(CardCombination(Card.string2card("2♣") :: Nil))), Hand(Nil, Nil)) equals true)
        pending
      }
    }

    describe("Validate a combination") {
      // TODO: Waiting for PROLOG part.
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
      it("Play a valid comb") {
        val c1 = Card.string2card("2♣")
        val c2 = Card.string2card("3♣")
        val c3 = Card.string2card("4♣")
        val comb = CardCombination(List(c1, c2, c3))
        val state = gameManager.create(ids)
        val played = gameManager.playCombination(Hand(), state.board, comb)
        // assert(played._2 contains c1)
        assert(!(played._1.tableCards contains c1))
      }
    }

    describe("Pick table cards") {
      it("Pick from table some cards") {
        val c1 = Card.string2card("2♣")
        val c2 = Card.string2card("3♣")
        val c3 = Card.string2card("4♣")
        val comb = CardCombination(List(c1, c2, c3))
        var state = gameManager.create(ids)
        val daniele = state getPlayer "Daniele" get
        val played = gameManager.playCombination(
          daniele.hand, state.board, comb)
        state = state.copy(board = played._2)
        daniele.hand = played._1
        state = state updatePlayer daniele
        val picked =
          gameManager.pickTableCards(
            (state getPlayer "Daniele" get).hand,
            state.board,
            c1)
        assert(picked._1.tableCards contains c1)
        assert(!(picked._1.playerCards contains c1))
      }
    }
  }
}