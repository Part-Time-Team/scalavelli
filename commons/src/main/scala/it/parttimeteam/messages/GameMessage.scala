package it.parttimeteam.messages

import akka.actor.ActorRef
import it.parttimeteam.PlayerAction
import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState

object GameMessage {

  /**
   * Client response to the game start message
   *
   * @param playerId           id of the player
   * @param gameClientActorRef ref of the client actor responsible for the communication with the server during the game
   */
  case class Ready(playerId: String, gameClientActorRef: ActorRef)

  /**
   * Message sent by the server to notify clients of game updates
   *
   * @param gameState the new game state
   */
  case class GameStateUpdated(gameState: PlayerGameState)

  /**
   * Tells the player it's his turn
   */
  case object PlayerTurn

  /**
   * Tells the player who is playing
   *
   * @param name name of the current player
   */
  case class OpponentInTurn(name: String)

  /**
   * Action made by the client
   *
   * @param playerId player identifier
   * @param action   action made by the player during the game
   */
  case class PlayerActionMade(playerId: String, action: PlayerAction)

  /**
   * Message sent by the client to leave the game
   *
   * @param playerId player identifier
   */
  case class LeaveGame(playerId: String)

  /**
   * an error sent by the server to the client
   *
   * @param errorType
   */
  case class MatchErrorOccurred(errorType: MatchError)

  /**
   * Send the drawn card to the current player
   *
   * @param card drawn card
   */
  case class CardDrawn(card: Card) // TODO MATTEOC remove

  /**
   * Tells the current player his turn is finished
   */
  case object TurnEnded


  /**
   * Tells the client the game ended with his victory
   */
  case object Won

  /**
   * Tells the client the game ended with a lost
   *
   * @param winnerPlayerName the name of the winner player
   */
  case class Lost(winnerPlayerName: String)


  case object GameEndedForPlayerLeft


  /**
   * Error occurred during a game
   */
  sealed class MatchError

  object MatchError {

    case object InvalidPlays extends MatchError

    case object PlayerActionNotValid extends MatchError

  }


}
