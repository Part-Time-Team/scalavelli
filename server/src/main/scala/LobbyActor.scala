import Messages.{ConnectUser, UserConnectionAccepted}
import akka.actor.{Actor, Props}

object LobbyActor {

  def props() = Props(new LobbyActor())

}

class LobbyActor extends Actor {

  override def receive: Receive = {
    case ConnectUser(_) => sender() ! UserConnectionAccepted
  }

}
