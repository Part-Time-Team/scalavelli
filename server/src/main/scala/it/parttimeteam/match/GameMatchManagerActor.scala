package it.parttimeteam.`match`

import akka.actor.{Actor, ActorLogging, PoisonPill, Props, Stash, Terminated}
import it.parttimeteam.`match`.GameMatchManagerActor.{GamePlayers, StateResult}
import it.parttimeteam.common.GamePlayer
import it.parttimeteam.core.GameState
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.player.Player.PlayerId
import it.parttimeteam.gamestate.{Opponent, PlayerGameState}
import it.parttimeteam.messages.GameMessage._
import it.parttimeteam.messages.LobbyMessages.MatchFound

import scala.concurrent.duration.DurationInt


object GameMatchManagerActor {
  def props(numberOfPlayers: Int, gameMatchManager: GameMatchManager): Props = Props(new GameMatchManagerActor(numberOfPlayers, gameMatchManager))


  case class StateResult(updatedState: GameState, additionalInformation: Option[AdditionalInfo])

  sealed class AdditionalInfo

  case class CardDrawnInfo(card: Card) extends AdditionalInfo

  /**
   * Sent to the gameactor to specify the players to add to the match
   *
   * @param players players to add to the match
   */
  case class GamePlayers(players: Seq[GamePlayer])

}

/**
 * Responsible for a game match
 *
 */
class GameMatchManagerActor(numberOfPlayers: Int, private val gameMatchManager: GameMatchManager)
  extends Actor with ActorLogging with Stash {

  override def receive: Receive = idle

  import context.dispatcher

  private var players: Seq[GamePlayer] = _
  private var turnManager: TurnManager[GamePlayer] = _

  private def idle: Receive = {
    case GamePlayers(players) => {
      log.info(s"initial players $players")
      this.players = players
      this.players.foreach(p => context.watch(p.actorRef))
      require(players.size == numberOfPlayers)
      this.broadcastMessageToPlayers(MatchFound(self))
      context.become(initializing(Seq.empty) orElse terminationBeforeGameStarted())
    }
  }

  /**
   * Waits all players to start game
   *
   * @param playersReady players ready for the game
   */
  private def initializing(playersReady: Seq[GamePlayer]): Receive = {
    case Ready(id, ref) => {
      this.withPlayer(id) { p =>
        log.info(s"player ${p.username} ready")

        val updatedReadyPlayers = playersReady :+ p.copy(actorRef = ref)

        if (updatedReadyPlayers.length == numberOfPlayers) {
          log.info("All players ready")
          this.initializeGame(updatedReadyPlayers)
        } else {
          context.become(initializing(updatedReadyPlayers) orElse terminationBeforeGameStarted())
        }
      }
    }
  }

  /**
   * Listen for termination messages before the match start:
   * before all the players sent the Ready message
   */
  private def terminationBeforeGameStarted(): Receive = {
    case Terminated(ref) => {
      this.players.find(_.actorRef == ref) match {
        case Some(player) => {
          log.info(s"player ${player.username} terminated before the game starts")
          // become in behaviour in cui a ogni ready che mi arriva invio il messaggio di fine partita
          // dopo x secondi mi uccido
          broadcastMessageToPlayers(GameEndedForPlayerLeft)
          context.become(gameEndedWithErrorBeforeStarts())
          context.system.scheduler.scheduleOnce(20.second) {
            log.info("Terminating game actor..")
            self ! PoisonPill
          }
        }
        case None => log.error(s"client with ref $ref not found")
      }
    }
  }


  /**
   * Listen for termination messages after the match start
   */
  private def terminationAfterGameStarted(): Receive = {
    case Terminated(ref) => this.players.find(_.actorRef == ref) match {
      case Some(player) =>
        log.info(s"Player ${player.username} left the game")
        this.broadcastMessageToPlayers(GameEndedForPlayerLeft)
        self ! PoisonPill
    }
    case LeaveGame(playerId) => withPlayer(playerId) { player =>
      log.info(s"Player ${player.username} left the game")
      this.broadcastMessageToPlayers(GameEndedForPlayerLeft)
      self ! PoisonPill
    }
  }

  /**
   * notify termination to next player if one of them terminates during the game loading 
   */
  private def gameEndedWithErrorBeforeStarts(): Receive = {
    case Ready(_, ref) => ref ! GameEndedForPlayerLeft
  }

  /**
   * Inizialize the game, creating the initial state, the turn manager and changing actor behaviour
   *
   * @param playersReady all the players ready
   */
  private def initializeGame(playersReady: Seq[GamePlayer]): Unit = {
    // unwatch the player with the old actor ref
    this.players.foreach(p => context.unwatch(p.actorRef))

    this.players = playersReady
    log.debug(s"ready players $playersReady")
    log.debug(s"updated players $players")

    // watch the players with the new actor ref
    this.players.foreach(p => context.watch(p.actorRef))

    this.turnManager = TurnManager[GamePlayer](players)
    log.info("initializing game..")
    val gameState = this.gameMatchManager.retrieveInitialState(players.map(p => (p.id, p.username)))
    this.broadcastGameStateToPlayers(gameState)
    val currentPlayer = this.turnManager.getInTurn
    currentPlayer.actorRef ! PlayerTurn
    this.broadcastMessageToNonCurrentPlayers(currentPlayer.id)(OpponentInTurn(currentPlayer.username))
    context.become(inTurn(gameState, currentPlayer) orElse terminationAfterGameStarted())
  }

  /**
   * Server behaviour during a player turn
   *
   * @param gameState    current game state
   * @param playerInTurn current player in turn
   */
  private def inTurn(gameState: GameState, playerInTurn: GamePlayer): Receive = {
    case PlayerActionMade(playerId, action) if playerId == playerInTurn.id => {
      log.info(s"received action $action from ${playerInTurn.username}")
      this.gameMatchManager.determineNextState(gameState, playerInTurn, action) match {
        case Right(stateResult) =>
          this.handleStateResult(stateResult, playerInTurn)

        case Left(error) =>
          log.error("Error resolving player action")
          playerInTurn.actorRef ! MatchErrorOccurred(error)
      }
    }
  }

  private def handleStateResult(stateResult: StateResult, playerInTurn: GamePlayer): Unit = {

    // notify the state
    this.broadcastGameStateToPlayers(stateResult.updatedState)

    // if game not ended
    if (!stateResult.updatedState.playerWon(playerInTurn.id)) {

      playerInTurn.actorRef ! TurnEnded

      // update the turn
      this.turnManager.nextTurn

      // notify the next player it's his turn
      this.turnManager.getInTurn.actorRef ! PlayerTurn
      this.broadcastMessageToNonCurrentPlayers(this.turnManager.getInTurn.id)(OpponentInTurn(this.turnManager.getInTurn.username))

      //switch the actor behaviour
      context.become(inTurn(stateResult.updatedState, this.turnManager.getInTurn))

    } else {
      log.info(s"Game ended, player ${playerInTurn.username} won")
      // game ended
      playerInTurn.actorRef ! Won
      this.broadcastMessageToNonCurrentPlayers(playerInTurn.id)(Lost(playerInTurn.username))

    }
  }


  /**
   * Broadcast a generic message to all game players
   *
   * @param message a generic message
   */
  private def broadcastMessageToPlayers(message: Any): Unit = {
    this.players.foreach(p => p.actorRef ! message)
  }

  private def broadcastMessageToNonCurrentPlayers(currentPlayerId: String)(message: Any): Unit = {
    this.players.filter(_.id != currentPlayerId).foreach(_.actorRef ! message)
  }

  /**
   * Send and update message about the game state to each player
   *
   * @param gameState the global game state
   */
  private def broadcastGameStateToPlayers(gameState: GameState) {

    println(gameState.toString)
    this.players.foreach(player => {
      player.actorRef ! GameStateUpdated(PlayerGameState(
        gameState.board,
        gameState.players.find(_.id == player.id).get.hand,
        gameState.players.filter(_.id != player.id).map(p => Opponent(p.name, p.hand.playerCards.size))
      ))

    })
  }


  private def withPlayer(playerId: PlayerId)(f: GamePlayer => Unit): Unit = {
    this.players.find(_.id == playerId) match {
      case Some(p) => f(p)
      case None => log.info(s"Player id $playerId not found")
    }
  }

}
