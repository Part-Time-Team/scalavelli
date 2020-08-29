package it.parttimeteam.model.game

import akka.actor.{Actor, ActorLogging, Props}
import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage.{CardDrawn, Error, GameStateUpdated, Lost, OpponentInTurn, PlayerTurn, TurnEnded, Won}

object RemoteGameActor {
  def props(listener: MatchServerResponseListener) = Props(new RemoteGameActor(listener))
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

    case CardDrawn(card) => this.listener.turnEndedWithCartDrawn(card)

    case TurnEnded => this.listener.turnEnded()

    case Won => this.listener.gameWon()

    case Lost(winnerName) => this.listener.gameLost(winnerName)

    case Error(_) =>
  }
}


trait MatchServerResponseListener {

  def gameStateUpdated(gameState: PlayerGameState)

  def turnStarted()

  def turnEnded()

  def opponentInTurn(opponentName: String)

  def turnEndedWithCartDrawn(card: Card)

  def gameWon()

  def gameLost(winnerName: String)

}