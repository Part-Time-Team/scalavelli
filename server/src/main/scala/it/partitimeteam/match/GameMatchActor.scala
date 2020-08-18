package it.partitimeteam.`match`

import akka.actor.{Actor, ActorLogging, Props, Stash}
import it.parttimeteam.Board
import it.parttimeteam.core.collections.Hand
import it.parttimeteam.entities.GamePlayer
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage.{GamePlayers, GameStarted, Ready}
import it.parttimeteam.messages.LobbyMessages.MatchFound

object GameMatchActor {
  def props(numberOfPlayers: Int): Props = Props(new GameMatchActor(numberOfPlayers))
}

class GameMatchActor(numberOfPlayers: Int) extends Actor with ActorLogging with Stash {

  override def receive: Receive = idle

  private def idle: Receive = {
    case GamePlayers(players) => {
      require(players.size == numberOfPlayers)
      this.broadcastMessageToPlayers(players)(MatchFound(self))
      context.become(initializing(players, Seq.empty))
      unstashAll()
    }
    case _ => stash()
  }

  private def initializing(players: Seq[GamePlayer], playersReady: Seq[GamePlayer]): Receive = {
    case Ready(id) => {
      players.find(p => p.id == id) match {
        case Some(p) => {
          val updatedReadyPlayers = playersReady :+ p
          if (updatedReadyPlayers.length == numberOfPlayers) {
            log.debug("All players ready")
            this.broadcastMessageToPlayers(players)(PlayerGameState(Board(), Hand(), Seq.empty))
            context.become(inGame())
          } else {
            context.become(initializing(players, updatedReadyPlayers))
          }
        }
        case None => log.debug(s"Player id $id not found")
      }
    }
  }

  private def inGame(): Receive = {
    case _ =>
  }

  private def broadcastMessageToPlayers(players: Seq[GamePlayer])(message: Any): Unit = {
    players.foreach(p => p.actorRef ! message)
  }

}
