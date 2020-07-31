package it.partitimeteam.`match`

import akka.actor.{Actor, ActorLogging, Props}

object GameMatchActor {
  def props(numberOfPlayers: Int): Props = Props(new GameMatchActor(numberOfPlayers))
}

class GameMatchActor(numberOfPlayers: Int) extends Actor with ActorLogging {

  override def receive: Receive = initializing

  private def initializing: Receive = {
    case _ =>
  }

}
