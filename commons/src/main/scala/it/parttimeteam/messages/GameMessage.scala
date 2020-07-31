package it.parttimeteam.messages

import it.parttimeteam.{GamePlayer, GameState}

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
   */
  case object Ready

  /**
   * Message sent by the server to notify clients of game updates
   *
   * @param gameState the new game state
   */
  case class GameStateUpdated(gameState: GameState)


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
  case class PlayerAction(playerId: String, action: PlayerAction)

  /**
   * Message sent by the client to leave the game
   *
   * @param playerId player identifier
   */
  case class LeaveGame(playerId: String)

}
