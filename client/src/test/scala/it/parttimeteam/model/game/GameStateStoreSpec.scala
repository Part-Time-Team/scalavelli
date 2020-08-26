package it.parttimeteam.model.game

import it.parttimeteam.Board
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{CardCombination, Hand}
import it.parttimeteam.gamestate.PlayerGameState
import org.scalatest.wordspec.AnyWordSpecLike

class GameStateStoreSpec extends AnyWordSpecLike {

  val initialState = PlayerGameState(
    Board.empty,
    Hand(List.empty, List.empty),
    Seq.empty
  )

  "a game store" should {

    "return an initial state when initialized" in {
      val gameStateStore = GameStateStore(initialState)
      assert(gameStateStore.currentState != null)
    }

  }

  "an initialized game state store" should {

    val sampleCard = Card("A", "♠", "B")

    "update the hand after a card has drawn" in {
      val gameStateStore = GameStateStore(initialState)
      assert(gameStateStore.onCardDrawn(sampleCard).hand.containsCards(sampleCard))
      gameStateStore.currentState.hand.containsCards(sampleCard)
    }

    "replace the state on a new state" in {
      val gameStateStore = GameStateStore(initialState)
      val updatedState = PlayerGameState(
        Board(Seq(CardCombination("id", Seq(sampleCard)))),
        Hand(List(sampleCard), List.empty),
        Seq.empty
      )

      assertResult(updatedState)(gameStateStore.onStateChanged(updatedState))
      assertResult(updatedState)(gameStateStore.currentState)

    }

    "replace the hand and the board when local turn state changed" in {
      val gameStateStore = GameStateStore(initialState)
      val updatedHand = Hand(List(sampleCard))
      val updatedBoard = Board(Seq(CardCombination("id", Seq(sampleCard))))
      val updatedState = gameStateStore.onLocalTurnStateChanged(updatedHand, updatedBoard)
      assertResult(updatedBoard)(updatedState.board)
      assertResult(updatedHand)(updatedState.hand)
      assertResult(updatedHand)(gameStateStore.currentState.hand)
      assertResult(updatedHand)(gameStateStore.currentState.hand)
    }

  }

}
