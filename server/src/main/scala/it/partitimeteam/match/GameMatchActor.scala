package it.partitimeteam.`match`

import akka.actor.{Actor, ActorLogging, Props}
import it.partitimeteam.common.GamePlayer

object GameMatchActor {
  def props(numberOfPlayers: Int, players: Seq[GamePlayer]): Props = Props(new GameMatchActor(numberOfPlayers, players))
}

class GameMatchActor(numberOfPlayers: Int, players: Seq[GamePlayer]) extends Actor with ActorLogging {

  override def receive: Receive = initializing

  private def initializing: Receive = {
    case _ =>
  }

}
