package lobby

import akka.actor.ActorRef
import akka.japi.Pair

class LobbyImpl extends Lobby {

  override def addClient(username: String, lobbyPreference: LobbyPreference, actorRef: ActorRef): Lobby = ???

  override def removeClient(username: String): Lobby = ???

  override def lobbyReadyForMatch(lobbyPreference: LobbyPreference): Boolean = ???

  override def lobbyWithExtractedMath(lobbyPreference: LobbyPreference): Pair[Lobby, Option[List[Pair[String, ActorRef]]]] = ???

}
