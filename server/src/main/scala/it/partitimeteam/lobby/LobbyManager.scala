package it.partitimeteam.lobby

import it.partitimeteam.common.Player

trait LobbyManager[T <: Player] {

  /**
   *
   * @param lobbyType type of the lobby
   * @param
   * @return
   */
  def addPlayer(player: Player, lobbyType: LobbyType): LobbyManager[T]

  /**
   *
   *
   * @return
   */
  def removePlayer(playerId: String): LobbyManager[T]

  /**
   *
   * @param lobbyType
   * @return the lobbing corresponding the the specified type, if present
   */
  def getLobby(lobbyType: LobbyType): Option[Lobby[T]]

}
