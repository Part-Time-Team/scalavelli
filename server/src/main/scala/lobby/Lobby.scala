package lobby

import akka.actor.ActorRef
import akka.japi.Pair

trait Lobby {

  def addClient(clientRef: ActorRef, lobbyPreference: LobbyPreference, clientData: ClientData): Lobby

  def removeClient(clientRef: ActorRef): Lobby

  def lobbyReadyForMatch(lobbyPreference: LobbyPreference): Boolean

  // TODO MATTEOC da vedere il secondo valore del pair
  def lobbyWithExtractedMath(lobbyPreference: LobbyPreference): Pair[Lobby, Option[List[Pair[ActorRef, ClientData]]]]

}
