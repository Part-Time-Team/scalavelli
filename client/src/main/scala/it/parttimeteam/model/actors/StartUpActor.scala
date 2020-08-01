package it.parttimeteam.model.actors

import akka.actor.{Actor, Props}
import it.parttimeteam.model.{GameStartUpEvent, LobbyJoinedEvent, PrivateLobbyCreated}

object StartUpActor {
  def props(notifyEvent: (GameStartUpEvent) => Unit) = Props(new StartUpActor(notifyEvent: (GameStartUpEvent) => Unit))
}

class StartUpActor(val notifyEvent: (GameStartUpEvent) => Unit) extends Actor{
  override def receive: Receive = {
    case _ => notifyEvent(LobbyJoinedEvent(""))
    case _ => notifyEvent(PrivateLobbyCreated("", ""))
    // TODO: Menage game start
    //case _ => notifyEvent(GameStarted())

    case _ =>
  }
}


