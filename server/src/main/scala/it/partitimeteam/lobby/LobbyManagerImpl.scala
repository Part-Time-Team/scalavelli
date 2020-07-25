package it.partitimeteam.lobby

import akka.actor.ActorRef

class LobbyManagerImpl extends LobbyManager {

  override def addClient(username: String, lobbyType: LobbyType, actorRef: ActorRef): LobbyManager = ???

  override def removeClient(username: String): LobbyManager = ???

  override def getLobby(lobbyType: LobbyType): Option[Lobby] = ???

}
