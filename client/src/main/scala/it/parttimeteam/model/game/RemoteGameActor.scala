package it.parttimeteam.model.game

import akka.actor.{Actor, ActorLogging, Props}
import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage._
import it.parttimeteam.model.game.RemoteGameActor.MatchServerResponseListener

object RemoteGameActor {
  def props(listener: MatchServerResponseListener) = Props(new RemoteGameActor(listener))

  trait MatchServerResponseListener {

    def gameStateUpdated(gameState: PlayerGameState)

    def turnStarted()

    def turnEnded()

    def opponentInTurn(opponentName: String)

    def turnEndedWithCartDrawn(card: Card)

    def gameEndedWithErrorEvent(reason: String)

    def gameWon()

    def gameLost(winnerName: String)

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

    case CardDrawn(card) => this.listener.turnEndedWithCartDrawn(card)

    case TurnEnded => this.listener.turnEnded()

    case Won => this.listener.gameWon()

    case Lost(winnerName) => this.listener.gameLost(winnerName)

    case GameEndedForPlayerLeft => this.listener.gameEndedWithErrorEvent("a player left the game")

    case MatchErrorOccurred(_) =>
  }
}


