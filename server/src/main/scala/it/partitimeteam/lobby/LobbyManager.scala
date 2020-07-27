package it.partitimeteam.lobby

import it.partitimeteam.common.Player

object LobbyManager {
  def apply(): LobbyManager[Player] = new LobbyManagerImpl[Player]()
}


trait LobbyManager[T <: Player] {

  /**
   * Add a player to the lobby system
   *
   * @param player    player to be added
   * @param lobbyType type of the lobby
   * @return a new lobby manger with the updated lobby
   */
  def addPlayer(player: Player, lobbyType: LobbyType): LobbyManager[T]

  /**
   * Remove a player from the lobby system
   *
   * @param playerId the id of the player to remove
   * @return a new lobbymanger without the specified player
   */
  def removePlayer(playerId: String): LobbyManager[T]

  /**
   *
   * @param lobbyType type of the lobby to retrieve
   * @return the lobbing corresponding the the specified type, if present
   */
  def getLobby(lobbyType: LobbyType): Option[Lobby[T]]

}


case class LobbyManagerImpl[T <: Player]() extends LobbyManager[T] {

  /**
   * @inheritdoc
   */
  override def addPlayer(player: Player, lobbyType: LobbyType): LobbyManager[T] = ???

  /**
   * @inheritdoc
   */
  override def removePlayer(playerId: String): LobbyManager[T] = ???

  /**
   * @inheritdoc
   */
  override def getLobby(lobbyType: LobbyType): Option[Lobby[T]] = ???
}

