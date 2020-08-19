package it.parttimeteam.model.game

import akka.actor.{Actor, ActorLogging, Props}
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage.{GameStateUpdated, PlayerTurn}

object RemoteGameActor {
  def props(listener: MatchServerResponseListener) = Props(new RemoteGameActor(listener))
}

class RemoteGameActor(private val listener: MatchServerResponseListener) extends Actor with ActorLogging {

  override def receive: Receive = {

    case GameStateUpdated(gameState) => {
      log.debug(s"Received game state $gameState")
      this.listener.onGameStateUpdated(gameState)
    }
    case PlayerTurn => {
      log.debug("Its my turn")
      this.listener.onTurnStarted()
    }

  }
}


trait MatchServerResponseListener {
  def onGameStateUpdated(gameState: PlayerGameState)

  def onTurnStarted()
}