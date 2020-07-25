package it.partitimeteam

import akka.actor.{Actor, Props}
import it.parttimeteam.messages.Messages.{ConnectUser, UserConnectionAccepted}

object LobbyActor {

  def props() = Props(new LobbyActor())

}

class LobbyActor extends Actor {

  override def receive: Receive = {
    case ConnectUser(userName, numberOfPlayers) => {
      sender() ! UserConnectionAccepted
    }
  }

}
