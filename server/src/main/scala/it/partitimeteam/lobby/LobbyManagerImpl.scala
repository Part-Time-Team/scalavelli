package it.partitimeteam.lobby

import it.partitimeteam.common.Player

class LobbyManagerImpl[T <: Player] extends LobbyManager[T] {
  /**
   *
   * @param lobbyType type of the lobby
   * @param
   * @return
   */
  override def addPlayer(player: Player, lobbyType: LobbyType): LobbyManager[T] = ???

  /**
   *
   *
   * @return
   */
  override def removePlayer(playerId: String): LobbyManager[T] = ???

  /**
   *
   * @param lobbyType
   * @return the lobbing corresponding the the specified type, if present
   */
  override def getLobby(lobbyType: LobbyType): Option[Lobby[T]] = ???
}
