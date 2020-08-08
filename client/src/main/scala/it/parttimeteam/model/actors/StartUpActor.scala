package it.parttimeteam.model.actors

import akka.actor.{Actor, ActorRef, Props}
import it.parttimeteam.messages.Messages.{JoinPublicLobby, LobbyJoinError, MatchFound, Stop}
import it.parttimeteam.model._

object StartUpActor {
  def props(notifyEvent: GameStartUpEvent => Unit) = Props(new StartUpActor(notifyEvent: GameStartUpEvent => Unit))
}

class StartUpActor(val notifyEvent: GameStartUpEvent => Unit) extends Actor {
  override def receive: Receive = {
    case JoinPublicLobby => notifyEvent(LobbyJoinedEvent(""))
    case PrivateLobbyCreatedEvent(generatedUserId: String, lobbyCode: String) => notifyEvent(PrivateLobbyCreatedEvent(generatedUserId, lobbyCode))
    case MatchFound(gameRoom: ActorRef) => notifyEvent(GameStartedEvent(gameRoom))
    case LobbyJoinError(reason: String) => notifyEvent(LobbyJoinErrorEvent(reason))
    case Stop() => context.stop(self)

    case _ =>
  }
}


