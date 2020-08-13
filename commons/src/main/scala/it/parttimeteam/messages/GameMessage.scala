package it.parttimeteam.messages

import it.parttimeteam.PlayerAction
import it.parttimeteam.entities.GamePlayer
import it.parttimeteam.gamestate.PlayerGameState

object GameMessage {

  /**
   * Sent to the gameactor to specify the players to add to the match
   *
   * @param players players to add to the match
   */
  case class GamePlayers(players: Seq[GamePlayer])

  /**
   * Message sent to the clients to notify the match start
   *
   */
  case object GameStarted

  /**
   * Client response to the game start message
   *
   * @param playerId id of the player
   */
  case class Ready(playerId: String)

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

}
