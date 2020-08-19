package it.partitimeteam.`match`

import akka.actor.{Actor, ActorLogging, Props, Stash}
import it.parttimeteam.GameState
import it.parttimeteam.core.GameManager
import it.parttimeteam.entities.GamePlayer
import it.parttimeteam.gamestate.{Opponent, PlayerGameState}
import it.parttimeteam.messages.GameMessage.{GamePlayers, PlayerActionMade, PlayerTurn, Ready}
import it.parttimeteam.messages.LobbyMessages.MatchFound

object GameMatchActor {
  def props(numberOfPlayers: Int, gameManager: GameManager): Props = Props(new GameMatchActor(numberOfPlayers, gameManager: GameManager))
}

class GameMatchActor(numberOfPlayers: Int, private val gameManager: GameManager) extends Actor with ActorLogging with Stash {

  override def receive: Receive = idle

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
    this.getPlayerForCurrentTurn.actorRef ! PlayerTurn
    context.become(inTurn(gameState, getPlayerForCurrentTurn))
  }

  private def inTurn(gameState: GameState, playerInTurn: GamePlayer): Receive = {
    case PlayerActionMade(playerId, action) if playerId == playerInTurn.id => {
      //TODO handle player action


      // TODO update the state
      val newState = gameState


      // notify the state
      this.broadcastGameStateToPlayers(gameState)

      // if game not ended
      if (true) { // TODO check game ended
        // update the turn
        val nextPlayerId = this.turnManager.nextTurn

        // notify the next player it's his turn
        this.getPlayerForCurrentTurn.actorRef ! PlayerTurn

        //switch the actor behaviour
        context.become(inTurn(newState, getPlayerForCurrentTurn))
      } else {

        // game ended

      }

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
    println(this.players.toString())

    println(gameState.toString)
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
