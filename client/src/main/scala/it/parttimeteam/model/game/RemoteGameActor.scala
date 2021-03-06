package it.parttimeteam.model.game

import akka.actor.{Actor, ActorLogging, Props}
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage.MatchError.PlayerActionNotValid
import it.parttimeteam.messages.GameMessage._
import it.parttimeteam.model.game.RemoteGameActor.MatchServerResponseListener

object RemoteGameActor {
  def props(listener: MatchServerResponseListener) = Props(new RemoteGameActor(listener))

  trait MatchServerResponseListener {

    def gameStateUpdated(gameState: PlayerGameState)

    def turnStarted()

    def turnEnded()

    def opponentInTurn(opponentName: String)

    def gameEndedBecausePlayerLeft()

    def gameWon()

    def gameLost(winnerName: String)

    def invalidPlayerAction()

  }

}

class RemoteGameActor(private val listener: MatchServerResponseListener) extends Actor with ActorLogging {

  override def receive: Receive = {

    case GameStateUpdated(gameState) => {
      log.debug(s"Received game state $gameState")
      this.listener.gameStateUpdated(gameState)
    }

    case PlayerTurn => {
      log.debug("Its my turn")
      this.listener.turnStarted()
    }

    case OpponentInTurn(name) => this.listener.opponentInTurn(name)

    case TurnEnded => this.listener.turnEnded()

    case Won => this.listener.gameWon()

    case Lost(winnerName) => this.listener.gameLost(winnerName)

    case GameEndedForPlayerLeft => this.listener.gameEndedBecausePlayerLeft()

    case MatchErrorOccurred(error) => error match {
      case PlayerActionNotValid => this.listener.invalidPlayerAction()
      case _ =>
    }
  }
}


