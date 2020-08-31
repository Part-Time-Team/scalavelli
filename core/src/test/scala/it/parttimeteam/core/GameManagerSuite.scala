package it.parttimeteam.core

import it.parttimeteam.core.cards.Card
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
    val c1: Card = Card.string2card("2♣R")
    val c2: Card = Card.string2card("3♣B")
    val c3: Card = Card.string2card("4♣B")
    val c4: Card = Card.string2card("5♣B")
    val c5: Card = Card.string2card("5♠R")
    val c6: Card = Card.string2card("5♦B")
    val c7: Card = Card.string2card("5♥R")

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
        val comb1 = CardCombination("#1", Seq(c1, c2, c3))
        val comb2 = CardCombination("#2", Seq(c5, c6, c7))
        val board = Board(Seq(comb1, comb2))
        val hand = Hand(List(c4))
        assert(gameManager validateTurn(board, hand))
      }

      describe("False with complex op") {
        it("With invalid board") {
          val comb1 = CardCombination("#1", Seq(c1, c2, c7))
          val comb2 = CardCombination("#2", Seq(c5, c6, c7))
          val board = Board(Seq(comb1, comb2))
          val hand = Hand(List(c4))
          assert(!(gameManager validateTurn(board, hand)))
        }

        it("With invalid hand") {
          val comb1 = CardCombination("#1", Seq(c1, c2, c3))
          val comb2 = CardCombination("#2", Seq(c5, c6, c7))
          val board = Board(Seq(comb1, comb2))
          val hand = Hand(tableCards = List(c4))
          assert(!(gameManager validateTurn(board, hand)))
        }
      }
    }

    describe("Play a combination") {
      val state = gameManager.create(playerInfos)

      it("Play a valid comb") {
        val seq = Seq(c1, c2, c3)
        val comb = CardCombination("#1", seq)
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
        val comb = CardCombination("#1", seq)
        val played = gameManager.playCombination(Hand(handSeq), state.board, comb)

        // Hand and Board must be the same as before the operation.
        assertResult(played._1)(Hand(handSeq))
        assertResult(played._2)(state.board)
      }
    }

    describe("Pick table cards") {
      it("Pick from table some cards") {
        val seq = Seq(c1, c2, c3)
        val comb = CardCombination("#2", seq)
        var state = gameManager.create(playerInfos)
        val daniele = (state getPlayer "1").get

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

    describe("Update a combination") {
      it("Update a simple combination") {
        val hand = Hand(Seq(c1, c2, c3, c4, c5))
        val comb = CardCombination("#1", Seq(c1, c2, c3))
        assert(comb.isValid)

        val board = Board(Seq(comb)).pickCards(Seq(c2, c3))
        assert(board.isRight)

        val res = gameManager.putCardsInCombination(hand, board.right.get, "#1", Seq(c2, c3))
        assertResult(Board(Seq(comb)))(res._2)
      }
    }
  }
}