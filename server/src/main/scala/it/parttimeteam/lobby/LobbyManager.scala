package it.parttimeteam.lobby

import it.parttimeteam.common.{GamePlayer, Player}

object LobbyManager {
  def apply(): LobbyManager[GamePlayer] = new LobbyManagerImpl[GamePlayer]()
}

/**
 * Mantains and update the list of all the lobbies available and of all the players inside them
 *
 * @tparam T
 */
trait LobbyManager[T <: Player] {


  /**
   * Add a player to the lobby system
   *
   * @param player    player to be added
   * @param lobbyType type of the lobby
   */
  def addPlayer(player: T, lobbyType: LobbyType): Unit

  /**
   * Remove a player from the lobby system
   *
   * @param playerId the id of the player to remove
   */
  def removePlayer(playerId: String): Unit

  /**
   *
   * @param lobbyType type of the lobby to retrieve
   * @return the lobbing corresponding the the specified type, if present
   */
  def getLobby(lobbyType: LobbyType): Option[Lobby[T]]

  /**
   * Tries to extract player from the lobby manager to start a match
   *
   * @param lobbyType type of the lobby
   * @return the list of player to be added to the game
   */
  def attemptExtractPlayerForMatch(lobbyType: LobbyType): Option[Seq[T]]

}


class LobbyManagerImpl[T <: Player] extends LobbyManager[T] {

  private var playersToLobby: Map[String, LobbyType] = Map.empty
  private var lobbies: Map[LobbyType, Lobby[T]] = Map.empty

  /**
   * @inheritdoc
   */
  override def addPlayer(player: T, lobbyType: LobbyType): Unit = {
    lobbies = lobbies.get(lobbyType) match {
      case Some(lobby) => lobbies + (lobbyType -> lobby.addPlayer(player))
      case None => lobbies + (lobbyType -> GameLobby(lobbyType.numberOfPlayers).addPlayer(player))
    }
    playersToLobby = playersToLobby + (player.id -> lobbyType)
  }

  /**
   * @inheritdoc
   */
  override def removePlayer(playerId: String): Unit = {
    playersToLobby.get(playerId) match {
      case Some(lobbyType) => lobbies.get(lobbyType) match {
        case Some(lobby) => lobby.removePlayer(playerId)
      }
    }
    playersToLobby = playersToLobby - playerId
  }

  /**
   * @inheritdoc
   */
  override def getLobby(lobbyType: LobbyType): Option[Lobby[T]] = lobbies.get(lobbyType)

  /**
   * @inheritdoc
   */
  override def attemptExtractPlayerForMatch(lobbyType: LobbyType): Option[Seq[T]] =
    this.getLobby(lobbyType).flatMap(lobby => {
      val updatedLobby = lobby.extractPlayersForMatch()
      lobbies = lobbies + (lobbyType -> updatedLobby.first)
      updatedLobby.second
    })
}

