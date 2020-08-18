package it.partitimeteam.`match`

import akka.actor.{Actor, ActorLogging, Props, Stash}
import it.parttimeteam.GameState
import it.parttimeteam.core.{GameManager, GameManagerImpl}
import it.parttimeteam.entities.GamePlayer
import it.parttimeteam.gamestate.{Opponent, PlayerGameState}
import it.parttimeteam.messages.GameMessage.{GamePlayers, PlayerActionMade, Ready}
import it.parttimeteam.messages.LobbyMessages.MatchFound

object GameMatchActor {
  def props(numberOfPlayers: Int): Props = Props(new GameMatchActor(numberOfPlayers))
}

class GameMatchActor(numberOfPlayers: Int) extends Actor with ActorLogging with Stash {

  override def receive: Receive = idle

  private val gameManager: GameManager = new GameManagerImpl()
  private var players: Seq[GamePlayer] = _
  private var turnManager: TurnManager = _

  private def idle: Receive = {
    case GamePlayers(players) => {
      this.players = players
      this.turnManager = TurnManager(players.map(_.id))
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
    this.broadcastGameStateToPlayers(gameState)
    context.become(inTurn(gameState, getPlayerForCurrentTurn))
  }

  private def inTurn(gameState: GameState, playerInTurn: GamePlayer): Receive = {
    case PlayerActionMade(playerId, action) if playerId == playerInTurn.id => {
      //handle player action

      // update the state

      // notify the state

      // update the turn

      // notify the next player it's his turn

    }
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
    this.players.foreach(player => {
      player.actorRef ! PlayerGameState(
        gameState.board,
        gameState.players.find(_.getId == player.id).get.hand,
        gameState.players.filter(_.getId != player.id).map(p => Opponent(p.getName, p.hand.playerCards.size))
      )

    })
    //this.broadcastMessageToPlayers(PlayerGameState(Board(), Hand(), Seq.empty))
  }

  private def getPlayerForCurrentTurn: GamePlayer =
    this.players.find(_.id == turnManager.playerInTurnId).get


  // TODO receive function for disconnection and error events

}
