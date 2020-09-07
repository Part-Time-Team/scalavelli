package it.parttimeteam.`match`

import akka.actor.{Actor, ActorLogging, PoisonPill, Props, Stash, Terminated}
import it.parttimeteam.`match`.GameMatchManagerActor.{CardDrawnInfo, GamePlayers, StateResult}
import it.parttimeteam.common.GamePlayer
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.player.Player.PlayerId
import it.parttimeteam.core.{GameInterface, GameState}
import it.parttimeteam.gamestate.{Opponent, PlayerGameState}
import it.parttimeteam.messages.GameMessage._
import it.parttimeteam.messages.LobbyMessages.MatchFound
import it.parttimeteam.messages.PlayerActionNotValidError


object GameMatchManagerActor {
  def props(numberOfPlayers: Int, gameApi: GameInterface): Props = Props(new GameMatchManagerActor(numberOfPlayers, gameApi: GameInterface))


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
 * @param numberOfPlayers number of players
 * @param gameApi
 */
class GameMatchManagerActor(numberOfPlayers: Int, private val gameApi: GameInterface)
  extends Actor with ActorLogging with Stash {

  override def receive: Receive = idle

  private var players: Seq[GamePlayer] = _
  private var turnManager: TurnManager[GamePlayer] = _

  // TODO spostare in costruzione a posto di gameApi
  private val gameMatchManager = new GameMatchManager(gameApi)

  private def idle: Receive = {
    case GamePlayers(players) => {
      this.players = players
      require(players.size == numberOfPlayers)
      this.broadcastMessageToPlayers(MatchFound(self))
      this.players.foreach(p => context.watch(p.actorRef))
      context.become(initializing(Seq.empty) orElse termination())
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
        log.debug(s"player ${p.username} ready")

        players = players.map(p => if (p.id == id) p.copy(actorRef = ref) else p)
        val updatedReadyPlayers = playersReady :+ p.copy(actorRef = ref)

        if (updatedReadyPlayers.length == numberOfPlayers) {
          log.debug("All players ready")
          this.initializeGame()
        } else {
          context.become(initializing(updatedReadyPlayers) orElse termination())
        }
      }
    }
  }

  private def termination(): Receive = {
    case Terminated(ref) => this.players.find(_.actorRef == ref) match {
      case Some(player) =>
        log.debug(s"Player ${player.username} left the game")
        this.broadcastMessageToPlayers(GameEndedForPlayerLeft)
        self ! PoisonPill
    }
    case LeaveGame(playerId) => withPlayer(playerId) { player =>
      log.debug(s"Player ${player.username} left the game")
      this.broadcastMessageToPlayers(GameEndedForPlayerLeft)
      self ! PoisonPill
    }
  }

  /**
   * Inizialize the game, creating the initial state, the turn manager and changing actor behaviour
   *
   */
  private def initializeGame(): Unit = {
    this.turnManager = TurnManager[GamePlayer](players)
    log.debug("initializing game..")
    val gameState = this.gameMatchManager.retrieveInitialState(players.map(p => (p.id, p.username)))
    this.broadcastGameStateToPlayers(gameState)
    this.turnManager.getInTurn.actorRef ! PlayerTurn
    context.become(inTurn(gameState, this.turnManager.getInTurn) orElse termination())
  }

  private def inTurn(gameState: GameState, playerInTurn: GamePlayer): Receive = {
    case PlayerActionMade(playerId, action) if playerId == playerInTurn.id => {

      this.gameMatchManager.determineNextState(gameState, playerInTurn, action) match {
        case Right(stateResult) =>
          this.handleStateResult(stateResult, playerInTurn)

        case Left(errorMessage) =>
          log.error("Error resolving player action")
          playerInTurn.actorRef ! PlayerActionNotValidError(errorMessage)
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
      log.debug(s"Game ended, player ${playerInTurn.username} won")
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
    //this.broadcastMessageToPlayers(PlayerGameState(Board(), Hand(), Seq.empty))
  }


  private def withPlayer(playerId: PlayerId)(f: GamePlayer => Unit): Unit = {
    this.players.find(_.id == playerId) match {
      case Some(p) => f(p)
      case None => log.debug(s"Player id $playerId not found")
    }
  }

}
