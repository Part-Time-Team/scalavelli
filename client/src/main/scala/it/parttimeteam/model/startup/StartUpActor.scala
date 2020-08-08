package it.parttimeteam.model.startup

import akka.actor.{Actor, ActorLogging, Props}
import it.parttimeteam.messages.LobbyMessages.{Connected, MatchFound, PrivateLobbyCreated, UserAddedToLobby}

object StartUpActor {
  def props(serverResponsesListener: StartupServerResponsesListener): Props = Props(new StartUpActor(serverResponsesListener))
}

/**
 * Actor responsible for receiving server lobby messages
 *
 * @param serverResponsesListener function user to notify back about the received event
 */
class StartUpActor(private val serverResponsesListener: StartupServerResponsesListener) extends Actor with ActorLogging {

  override def receive: Receive = {
    case Connected(id) => this.serverResponsesListener.connected(id, sender())
    case UserAddedToLobby() => this.serverResponsesListener.addedToLobby()
    case PrivateLobbyCreated(lobbyCode) => this.serverResponsesListener.privateLobbyCreated(lobbyCode)
    case MatchFound(gameRoom) => this.serverResponsesListener.matchFound(gameRoom)
    case m: String => log.debug(m)
  }


}


