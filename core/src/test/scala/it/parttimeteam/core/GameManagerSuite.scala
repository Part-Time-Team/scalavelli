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

      describe("When there are player to add") {
        val list = ids.map(i => Player("", i, Hand(List.empty, List.empty)))
        val state = gameManager.create(ids)

        it("Game state players are not empty") {
          assert(state.players.nonEmpty)
        }

        it("Players have their hands filled with 13 cards") {
          assert(state.players.forall(p => p.hand.playerCards.size == 13))
        }
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
      }
    }

    describe("Validate a turn") {
      val stubManager = stub[GameManager]
      // If the board is empty and the hand too, return a valid turn.
      stubManager.validateTurn _ when(Board(Nil), Hand(Nil, Nil)) returns true

      // If the board is filled with a combination and the hand is empty, return a valid turn
      stubManager.validateTurn _ when(Board(Seq(CardCombination("#1", Seq(Card.string2card("2♣R"))))), Hand(Nil, Nil)) returns true
      // TODO: Waiting for PROLOG part.
      it("With complex op") {
        // assert(stubManager.validateTurn(Board(Nil), Hand(Nil, Nil)) equals true)
        // assert(stubManager.validateTurn(Board(List(CardCombination(Card.string2card("2♣R") :: Nil))), Hand(Nil, Nil)) equals true)
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

    // TODO: Check when a player have played a combination in this hand and not.
    describe("Play a combination") {
      it("Play a valid comb") {
        val state = gameManager.create(ids)
        val c1 = Card.string2card("2♣R")
        val seq = Seq(c1, Card.string2card("3♣B"), Card.string2card("4♣B"))
        val comb = CardCombination("#1", seq)
        val played = gameManager.playCombination(Hand(seq.toList), state.board, comb)
        assert(!(played._1 containsCards c1))
        assert(played._2.combinations contains comb)
      }
    }

    // TODO: Check whan a player pick up a combination that is present or not.
    describe("Pick table cards") {
      it("Pick from table some cards") {
        val c1 = Card.string2card("2♣R")
        val seq = Seq(c1, Card.string2card("3♣B"), Card.string2card("4♣B"))
        val comb = CardCombination("#2", seq)
        var state = gameManager.create(ids)
        val daniele = (state getPlayer "Daniele").get

        // First play a combination.
        val played = gameManager.playCombination(
          Hand(seq.toList), state.board, comb)
        state = state.copy(board = played._2)
        daniele.hand = played._1
        state = state updatePlayer daniele

        // Second try to pick a combination from the board.
        val picked = gameManager.pickBoardCards(
          Hand(),
          state.board,
          c1)
        assert(picked.isRight)

        // This is make secure by previous assertion.
        val (hand: Hand, board: Board) = picked.right.get
        assert(hand.tableCards contains c1)
        assert(!(hand.playerCards contains c1))
        assert(board.combinations forall (c => !(c.cards contains c1)))
      }
    }
  }
}
