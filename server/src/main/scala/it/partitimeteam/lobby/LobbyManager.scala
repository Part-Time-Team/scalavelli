package it.partitimeteam.lobby

import it.parttimeteam.entities.Player


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

}


class LobbyManagerImpl[T <: Player] extends LobbyManager[T] {

  private var playersToLobby: Map[String, LobbyType] = Map()
  private var lobbies: Map[LobbyType, Lobby[T]] = Map()

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
}

