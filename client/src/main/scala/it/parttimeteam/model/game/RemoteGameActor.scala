package it.parttimeteam.model.game

import akka.actor.{Actor, Props}
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage.{GameStateUpdated, PlayerTurn}

object RemoteGameActor {
  def props(listener: MatchServerResponseListener) = Props(new RemoteGameActor(listener))
}

class RemoteGameActor(private val listener: MatchServerResponseListener) extends Actor {

  override def receive: Receive = {

    case GameStateUpdated(gameState) =>
    case PlayerTurn =>

  }
}


trait MatchServerResponseListener {
  def onGameStateUpdated(gameState: PlayerGameState)
  def onTurnStarted()
}