package it.parttimeteam.model.game

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{Board, Hand}
import it.parttimeteam.gamestate.PlayerGameState

/**
 * Maintains and updates the local game state
 */
trait GameStateStore {

  def currentState: PlayerGameState

  /**
   * Add the card drown to the player hand
   *
   * @param card the card drawn
   * @return the updated state
   */
  def onCardDrawn(card: Card): PlayerGameState

  /**
   * Replace the current state with the new one
   *
   * @param state the new state
   * @return the updated state
   */
  def onStateChanged(state: PlayerGameState): PlayerGameState

  /** *
   * Updates local state after:
   * * a card combination picked
   * * a card combination made
   * * a card combination updated
   *
   * @param hand  the player hand
   * @param board the  game board
   * @return the updated state
   */
  def onLocalTurnStateChanged(hand: Hand, board: Board): PlayerGameState

}

object GameStateStore {

  def apply(initialState: PlayerGameState): GameStateStore = new GameStateStoreImpl(initialState)

  /**
   *
   * @param currentState the initial state
   */
  private class GameStateStoreImpl(var currentState: PlayerGameState) extends GameStateStore {

    override def onCardDrawn(card: Card): PlayerGameState = {
      currentState = currentState.copy(hand = currentState.hand.copy(playerCards = card :: currentState.hand.playerCards))
      currentState
    }

    override def onStateChanged(state: PlayerGameState): PlayerGameState = {
      currentState = state
      currentState
    }

    override def onLocalTurnStateChanged(hand: Hand, board: Board): PlayerGameState = {
      currentState = currentState.copy(hand = hand, board = board)
      currentState
    }

  }

}


