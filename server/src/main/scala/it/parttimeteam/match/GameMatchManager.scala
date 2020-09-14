package it.parttimeteam.`match`

import it.parttimeteam.{DrawCard, PlayedMove, PlayerAction}
import it.parttimeteam.`match`.GameMatchActor.{CardDrawnInfo, StateResult}
import it.parttimeteam.common.GamePlayer
import it.parttimeteam.core.collections.{Board, Hand}
import it.parttimeteam.core.player.Player.{PlayerId, PlayerName}
import it.parttimeteam.core.{GameInterface, GameState}
import it.parttimeteam.messages.GameMessage.MatchError

class GameMatchManager(private val gameApi: GameInterface) {


  def retrieveInitialState(players: Seq[(PlayerId, PlayerName)]): GameState =
    gameApi.create(players)


  /**
   * Determines the next game state based on the player action
   *
   * @param currentState current state of the game
   * @param playerInTurn player making the action
   * @param playerAction action made my the player
   * @return a state result or a string representing an error
   */
  def determineNextState(currentState: GameState, playerInTurn: GamePlayer, playerAction: PlayerAction): Either[MatchError, StateResult] = {
    playerAction match {
      case DrawCard => nextStateOnCardDrawn(currentState, playerInTurn)
      case PlayedMove(updatedHand, updatedBoard) => nextStateOnPlayerMove(currentState, playerInTurn, updatedHand, updatedBoard)
    }
  }

  private def nextStateOnPlayerMove(currentState: GameState, playerInTurn: GamePlayer, updatedHand: Hand, updatedBoard: Board) = {
    if (gameApi.validateMove(updatedBoard, updatedHand)) {
      val updatedState = currentState
        .getPlayer(playerInTurn.id)
        .map(p => currentState.updatePlayer(p.copy(
          hand = updatedHand)))
        .get.copy(board = updatedBoard)

      Right(StateResult(
        updatedState = updatedState,
        additionalInformation = None
      ))

    } else {
      Left(MatchError.PlayerActionNotValid)
    }
  }

  private def nextStateOnCardDrawn(currentState: GameState, playerInTurn: GamePlayer) = {
    val (updateDeck, cardDrawn) = gameApi.draw(currentState.deck)

    // updated player with the new card on his hand
    val updatedState = currentState
      .getPlayer(playerInTurn.id)
      .map(p => currentState.updatePlayer(p.copy(
        hand = p.hand.copy(playerCards = cardDrawn +: p.hand.playerCards))))
      .get.copy(deck = updateDeck)

    Right(StateResult(
      updatedState = updatedState,
      additionalInformation = Some(CardDrawnInfo(cardDrawn))
    ))
  }
}
