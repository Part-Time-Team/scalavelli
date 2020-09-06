package it.parttimeteam.core

import it.parttimeteam.core.TestCards._
import it.parttimeteam.core.collections.{Board, CardCombination, Deck, Hand}
import org.scalamock.matchers.Matchers
import org.scalamock.scalatest.MockFactory
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.an

class GameManagerSuite extends AnyFunSpec with MockFactory with Matchers {
  describe("A game manager") {
    // Use the same instance of GameManager for all tests.
    val gameManager: GameManager = new GameManagerImpl()
    val playerInfos = Seq(("1", "Daniele"), ("2", "Lorenzo"), ("3", "Luca"), ("4", "Matteo"))

    describe("Can create a game") {
      it("Empty if no players are added") {
        assert(gameManager.create(Seq.empty).players equals Seq.empty)
      }

      describe("When there are player to add") {
        val state = gameManager.create(playerInfos)

        it("Game state players are created") {
          assert(state.players.nonEmpty)
          assert(state.players.map(m => (m.id, m.name)) equals playerInfos)
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

    describe("Validate a turn") {
      // TODO: Waiting for PROLOG part.
      it("True with complex op") {
        val comb1 = CardCombination("#1", Seq(TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS))
        val comb2 = CardCombination("#2", Seq(FIVE_SPADES, FIVE_DIAMONDS, FIVE_HEARTS))
        val board = Board(Seq(comb1, comb2))
        val hand = Hand(List(FIVE_CLUBS))
        assert(gameManager validateTurn(board, hand))
      }

      describe("False with complex op") {
        it("With invalid board") {
          val comb1 = CardCombination("#1", Seq(TWO_CLUBS, THREE_CLUBS, FIVE_HEARTS))
          val comb2 = CardCombination("#2", Seq(FIVE_SPADES, FIVE_DIAMONDS, FIVE_HEARTS))
          val board = Board(Seq(comb1, comb2))
          val hand = Hand(List(FIVE_CLUBS))
          assert(!(gameManager validateTurn(board, hand)))
        }

        it("With invalid hand") {
          val comb1 = CardCombination("#1", Seq(TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS))
          val comb2 = CardCombination("#2", Seq(FIVE_SPADES, FIVE_DIAMONDS, FIVE_HEARTS))
          val board = Board(Seq(comb1, comb2))
          val hand = Hand(boardCards = List(FIVE_CLUBS))
          assert(!(gameManager validateTurn(board, hand)))
        }
      }
    }

    describe("Play a combination") {
      val state = gameManager.create(playerInfos)

      it("Play a valid comb") {
        val cards = Seq(TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS)
        val result = gameManager.playCombination(Hand((FIVE_CLUBS +: cards).toList), state.board, cards)
        assert(result.isRight)
        val played = result.right.get
        // Check that seq cards are not in player hand.
        assert(played._1.playerCards.nonEmpty)
        assert(played._1.boardCards.isEmpty)
        assertResult(Seq(FIVE_CLUBS))(played._1.playerCards)
        // Check that board contain the new combination.
        assert(played._2.combinations.exists(_.cards == cards))
      }

      it("Play cards that are not present in the hand returns error") {
        val cards = Seq(TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS)
        val handSeq = List(THREE_CLUBS, FOUR_CLUBS, FIVE_CLUBS)
        val result = gameManager.playCombination(Hand(handSeq), state.board, cards)
        assert(result.isLeft)
      }
    }

    describe("Pick table cards") {
      it("Pick from table some cards") {
        val seq = Seq(TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS)
        val comb = CardCombination("#2", seq)
        var state = gameManager.create(playerInfos)
        val daniele = (state getPlayer "1").get

        // First play a combination.
        val result = gameManager.playCombination(
          Hand(seq.toList), state.board, comb.cards)
        assert(result.isRight)
        val played = result.right.get
        state = state.copy(board = played._2)
        daniele.hand = played._1
        state = state updatePlayer daniele

        // Second try to pick a combination from the board.
        val picked = gameManager.pickBoardCards(
          Hand(),
          state.board,
          List(TWO_CLUBS))
        assert(picked.isRight)

        // This is make secure by previous assertion.
        val (hand: Hand, board: Board) = picked.right.get
        assert(hand.boardCards contains TWO_CLUBS)
        assert(!(hand.playerCards contains TWO_CLUBS))
        assert(board.combinations forall (c => !(c.cards contains TWO_CLUBS)))
      }
    }

    describe("Update a combination") {
      it("Update a simple combination") {
        val hand = Hand(Seq(TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS, FIVE_CLUBS, FIVE_SPADES))
        val comb = CardCombination("#1", Seq(TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS))
        assert(comb.isValid)

        val board = Board(Seq(comb)).pickCards(Seq(THREE_CLUBS, FOUR_CLUBS))
        assert(board.isRight)

        val res = gameManager.putCardsInCombination(hand, board.right.get, "#1", Seq(THREE_CLUBS, FOUR_CLUBS))
        assertResult(Board(Seq(comb)))(res._2)
      }
    }
  }
}