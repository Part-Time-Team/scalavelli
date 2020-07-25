package it.partitimeteam.lobby

import akka.actor.ActorRef
import it.partitimeteam.common.Referable

class LobbyManagerImpl[T <: Referable] extends LobbyManager[T] {

  override def addClient(username: String, lobbyType: LobbyType, actorRef: ActorRef): LobbyManager[T] = ???

  override def removeClient(username: String): LobbyManager[T] = ???

  override def getLobby(lobbyType: LobbyType): Option[Lobby[T]] = ???

}
