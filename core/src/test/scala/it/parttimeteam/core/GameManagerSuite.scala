package it.parttimeteam.core

import it.parttimeteam.Board
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{CardCombination, Deck, Hand}
import org.scalamock.matchers.Matchers
import org.scalamock.scalatest.MockFactory
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.an

class GameManagerSuite extends AnyFunSpec with MockFactory with Matchers {
  describe("A game manager") {
    // Use the same instance of GameManager for all tests.
    val gameManager: GameManager = new GameManagerImpl()
    val ids = Seq("Daniele", "Lorenzo", "Luca", "Matteo")

    describe("Can create a game") {
      it("Empty if no players are added") {
        assert(gameManager.create(Seq.empty).players equals Seq.empty)
      }

      describe("When there are player to add") {
        val state = gameManager.create(ids)

        it("Game state players are created") {
          assert(state.players.nonEmpty)
          assert(state.players.map(m => m.id) equals ids)
          assert(state.players.forall(p => p.hand.playerCards.size == 13))
        }
      }
    }

    describe("Draw cards from a deck") {
      val deck = Deck.shuffled

      it("Draw cards from a non empty deck") {
        val drawnDeck = Deck.cards2deck(deck.cards.tail)
        assert(gameManager.draw(deck) equals(drawnDeck, deck.cards.head))
      }

      it("Throw an exception when drawing from an empty deck") {
        val emptyDeck = Deck.empty
        an[UnsupportedOperationException] should be thrownBy
          (gameManager draw emptyDeck)
      }
    }

    // TODO: Check when a player have played a combination in this hand and not.
    describe("Play a combination") {
      val state = gameManager.create(ids)
      val c1 = Card.string2card("2♣R")
      val c2 = Card.string2card("3♣B")
      val c3 = Card.string2card("4♣B")
      val c4 = Card.string2card("5♣B")

      it("Play a valid comb") {
        val seq = Seq(c1, c2, c3)
        val comb = CardCombination(seq)
        val played = gameManager.playCombination(Hand((c4 +: seq).toList), state.board, comb)

        // Check that seq cards are not in player hand.
        assert(played._1.playerCards.nonEmpty)
        assert(played._1.tableCards.isEmpty)
        assertResult(Seq(c4))(played._1.playerCards)
        // Check that board contain the new combination.
        assert(played._2.combinations contains comb)
      }

      it("Play cards that are not present in the hand") {
        val seq = Seq(c1, c2, c3)
        val handSeq = List(c2, c3, c4)
        val comb = CardCombination(seq)
        val played = gameManager.playCombination(Hand(handSeq), state.board, comb)

        // Hand and Board must be the same as before the operation.
        assertResult(played._1)(Hand(handSeq))
        assertResult(played._2)(state.board)
      }
    }

    // TODO: Check whan a player pick up a combination that is present or not.
    describe("Pick table cards") {
      it("Pick from table some cards") {
        val c1 = Card.string2card("2♣R")
        val list = List(c1, Card.string2card("3♣B"), Card.string2card("4♣B"))
        val comb = CardCombination(list)
        var state = gameManager.create(ids)
        val daniele = (state getPlayer "Daniele").get

        // First play a combination.
        val played = gameManager.playCombination(Hand(list), state.board, comb)
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

    describe("Validate a turn") {
      // TODO: Waiting for PROLOG part.
      it("With complex op") {
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
  }
}
