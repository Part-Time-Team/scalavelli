package it.parttimeteam.core

import it.parttimeteam.core.TestCards._
import it.parttimeteam.core.collections.{Board, CardCombination, Deck, Hand}
import org.scalamock.matchers.Matchers
import org.scalamock.scalatest.MockFactory
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.an

class GameInterfaceSuite extends AnyFunSpec with MockFactory with Matchers {
  describe("A game manager") {
    // Use the same instance of GameInterface for all tests.
    val gameInterface: GameInterface = new GameInterfaceImpl()
    val playerInfos = Seq(("1", "Daniele"), ("2", "Lorenzo"), ("3", "Luca"), ("4", "Matteo"))
    val comb1 = CardCombination("#1", Seq(TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS))
    val comb2 = CardCombination("#2", Seq(FIVE_SPADES, FIVE_DIAMONDS, FIVE_HEARTS))
    val comb3 = CardCombination("#3", Seq(TWO_CLUBS, THREE_CLUBS, FIVE_HEARTS))
    val comb4 = CardCombination("#4", Seq(FIVE_SPADES, FIVE_DIAMONDS, FIVE_HEARTS))

    describe("Can create a game") {
      it("Empty if no players are added") {
        assert(gameInterface.create(Seq.empty).players equals Seq.empty)
      }

      describe("When there are player to add") {
        val state = gameInterface.create(playerInfos)

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
        assert(gameInterface.draw(deck) equals(drawnDeck, deck.cards.head))
      }

      it("Throw an exception when drawing from an empty deck") {
        val emptyDeck = Deck.empty
        an[UnsupportedOperationException] should be thrownBy
          (gameInterface draw emptyDeck)
      }
    }

    describe("Validate a move") {
      it("True with complex op") {
        val board = Board(Seq(comb1, comb2))
        val hand = Hand(Seq(FIVE_CLUBS))
        assert(gameInterface validateMove(board, hand))
      }

      describe("False with complex op") {
        it("With invalid board") {
          val board = Board(Seq(comb3, comb4))
          val hand = Hand(Seq(FIVE_CLUBS))
          assert(!(gameInterface validateMove(board, hand)))
        }

        it("With invalid hand") {
          val board = Board(Seq(comb1, comb2))
          val hand = Hand(boardCards = Seq(FIVE_CLUBS))
          assert(!(gameInterface validateMove(board, hand)))
        }
      }
    }

    describe("Validate a turn") {
      it("True with complex op") {
        val startBoard = Board(Seq(comb1, comb2))
        val startHand = Hand(Seq(FIVE_CLUBS, SIX_CLUBS, SEVEN_CLUBS, EIGHT_CLUBS))
        val combTmp = CardCombination("#tmp", Seq(SIX_CLUBS, SEVEN_CLUBS, EIGHT_CLUBS))
        val board = Board(Seq(comb1, comb2, combTmp))
        val hand = Hand(Seq(FIVE_CLUBS))
        assert(gameInterface validateTurn(board, startBoard, hand, startHand))
      }
    }

    describe("Play a combination") {
      val state = gameInterface.create(playerInfos)

      it("Play a valid comb") {
        val cards = Seq(TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS)
        val result = gameInterface.playCombination(Hand((FIVE_CLUBS +: cards).toList), state.board, cards)
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
        val handSeq = Seq(THREE_CLUBS, FOUR_CLUBS, FIVE_CLUBS)
        val result = gameInterface.playCombination(Hand(handSeq), state.board, cards)
        assert(result.isLeft)
      }

      it("Play a combination with on overflow ace") {
        val handSeq = List(JACK_HEARTS, ACE_HEARTS, QUEEN_HEARTS, KING_HEARTS)
        val cards = Seq(JACK_HEARTS, ACE_HEARTS, QUEEN_HEARTS, KING_HEARTS)
        val result = gameInterface.playCombination(Hand(handSeq), state.board, cards)
        assert(result.isRight)
      }

      it("Play a combination with a royal flush") {

        val handSeq = Seq(ACE_CLUBS_RED, TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS, FIVE_CLUBS, SIX_CLUBS, SEVEN_CLUBS, EIGHT_CLUBS, NINE_CLUBS, TEN_CLUBS, JACK_CLUBS, ACE_CLUBS_BLUE, QUEEN_CLUBS, KING_CLUBS)
        val cards =   Seq(ACE_CLUBS_RED, TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS, FIVE_CLUBS, SIX_CLUBS, SEVEN_CLUBS, EIGHT_CLUBS, NINE_CLUBS, TEN_CLUBS, JACK_CLUBS, ACE_CLUBS_BLUE, QUEEN_CLUBS, KING_CLUBS)
        val result = gameInterface.playCombination(Hand(handSeq), state.board, cards)
        assert(result.isRight)
      }
    }

    describe("Pick table cards") {
      it("Pick from table some cards") {
        val seq = Seq(TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS)
        val comb = CardCombination("#2", seq)
        var state = gameInterface.create(playerInfos)
        val daniele = (state getPlayer "1").get

        // First play a combination.
        val result = gameInterface.playCombination(
          Hand(seq.toList), state.board, comb.cards)
        assert(result.isRight)
        val played = result.right.get
        state = state.copy(board = played._2)
        daniele.hand = played._1
        state = state updatePlayer daniele

        // Second try to pick a combination from the board.
        val picked = gameInterface.pickBoardCards(
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

        val res = gameInterface.putCardsInCombination(hand, board.right.get, "#1", Seq(THREE_CLUBS, FOUR_CLUBS))
        assertResult(Board(Seq(comb)))(res.right.get._2)
      }
    }
  }
}