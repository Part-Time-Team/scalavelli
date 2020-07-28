package it.partitimeteam.lobby

import akka.actor.{Actor, Props}
import it.parttimeteam.messages.Messages.{ConnectUserToPublicLobby, UserConnectionAccepted}

object LobbyManagerActor {

  def props() = Props(new LobbyManagerActor())

}

class LobbyManagerActor extends Actor {

  override def receive: Receive = {
    case ConnectUserToPublicLobby(userName, numberOfPlayers) => {
      sender() ! UserConnectionAccepted
    }
  }

}
