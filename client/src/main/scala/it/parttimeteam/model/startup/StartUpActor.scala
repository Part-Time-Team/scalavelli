package it.parttimeteam.model.startup

import akka.actor.{Actor, ActorLogging, Props}

object StartUpActor {
  def props(serverResponsesListener: StartupServerResponsesListener): Props = Props(new StartUpActor(serverResponsesListener))
}

class StartUpActor(private val serverResponsesListener: StartupServerResponsesListener) extends Actor with ActorLogging {


  override def receive: Receive = {
    case _ =>
  }

  //  private def receiveFromServer: Receive = {
  //    case JoinPublicLobby => notifyEvent(LobbyJoinedEvent(""))
  //    case PrivateLobbyCreatedEvent(generatedUserId: String, lobbyCode: String) => notifyEvent(PrivateLobbyCreatedEvent(generatedUserId, lobbyCode))
  //    case MatchFound(gameRoom: ActorRef) => notifyEvent(GameStartedEvent(gameRoom))
  //    case LobbyJoinError(reason: String) => notifyEvent(LobbyJoinErrorEvent(reason))
  //    case Stop() => context.stop(self)
  //    case _ =>
  //  }


}


