package it.partitimeteam.`match`

import akka.actor.{Actor, ActorLogging, Props, Stash}
import it.parttimeteam.core.collections.Hand
import it.parttimeteam.core.{GameManager, GameManagerImpl}
import it.parttimeteam.entities.GamePlayer
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage.{GamePlayers, Ready}
import it.parttimeteam.messages.LobbyMessages.MatchFound
import it.parttimeteam.{Board, GameState}

object GameMatchActor {
  def props(numberOfPlayers: Int): Props = Props(new GameMatchActor(numberOfPlayers))
}

class GameMatchActor(numberOfPlayers: Int) extends Actor with ActorLogging with Stash {

  override def receive: Receive = idle

  private val gameManager: GameManager = new GameManagerImpl()
  private var players: Seq[GamePlayer] = _

  private def idle: Receive = {
    case GamePlayers(players) => {
      this.players = players
      require(players.size == numberOfPlayers)
      this.broadcastMessageToPlayers(MatchFound(self))
      context.become(initializing(Seq.empty))
      unstashAll()
    }
    case _ => stash()
  }

  /**
   * Waits all players to start game
   *
   * @param playersReady players ready for the game
   */
  private def initializing(playersReady: Seq[GamePlayer]): Receive = {
    case Ready(id) => {
      this.players.find(_.id == id) match {
        case Some(p) => {
          val updatedReadyPlayers = playersReady :+ p
          if (updatedReadyPlayers.length == numberOfPlayers) {
            log.debug("All players ready")
            this.initializeGame()

          } else {
            context.become(initializing(updatedReadyPlayers))
          }
        }
        case None => log.debug(s"Player id $id not found")
      }
    }
  }

  /**
   * Inizialize the game, creating the initial state, the turn manager and changing actor behaviour
   *
   */
  private def initializeGame(): Unit = {
    val gameState = gameManager.create(players.map(_.id))
    context.become(inGame(gameState, TurnManager(players.map(_.id))))
    this.broadcastGameStateToPlayers(gameState)
  }

  private def inGame(gameState: GameState, turnManager: TurnManager): Receive = {
    case _ =>
  }


  private def broadcastMessageToPlayers(message: Any): Unit = {
    this.players.foreach(p => p.actorRef ! message)
  }

  /**
   * Send and update message about the game state to each player
   *
   * @param gameState the global game state
   */
  private def broadcastGameStateToPlayers(gameState: GameState) {
    this.broadcastMessageToPlayers(PlayerGameState(Board(), Hand(), Seq.empty))
  }

}
