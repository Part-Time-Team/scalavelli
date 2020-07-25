package it.partitimeteam.lobby

import akka.actor.ActorRef
import akka.japi.Pair

trait Lobby {

  def addClient(username: String, lobbyPreference: LobbyPreference, actorRef: ActorRef): Lobby

  def removeClient(username: String): Lobby

  def lobbyReadyForMatch(lobbyPreference: LobbyPreference): Boolean

  def lobbyWithExtractedMath(lobbyPreference: LobbyPreference): Pair[Lobby, Option[List[Pair[String, ActorRef]]]]

}
