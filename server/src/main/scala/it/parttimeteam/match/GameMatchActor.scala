package it.parttimeteam.`match`

import akka.actor.{Actor, ActorLogging, Props, Stash}
import it.parttimeteam.`match`.GameMatchActor.{CardDrawnInfo, GamePlayers, StateResult}
import it.parttimeteam.common.GamePlayer
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.{GameManager, GameState}
import it.parttimeteam.gamestate.{Opponent, PlayerGameState}
import it.parttimeteam.messages.GameMessage._
import it.parttimeteam.messages.LobbyMessages.MatchFound
import it.parttimeteam.messages.PlayerActionNotValidError
import it.parttimeteam.{DrawCard, PlayedMove, PlayerAction}

object GameMatchActor {
  def props(numberOfPlayers: Int, gameManager: GameManager): Props = Props(new GameMatchActor(numberOfPlayers, gameManager: GameManager))


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
 * @param gameManager
 */
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
    case Ready(id, ref) => {
      this.players.find(_.id == id) match {
        case Some(p) => {
          log.debug(s"player ${p.username} ready")
          players = players.map(p => if (p.id == id) p.copy(actorRef = ref) else p)
          val updatedReadyPlayers = playersReady :+ p.copy(actorRef = ref)

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
    log.debug("initializing game..")
    val gameState = gameManager.create(players.map(_.id))
    this.broadcastGameStateToPlayers(gameState)
    this.getPlayerForCurrentTurn.actorRef ! PlayerTurn
    context.become(inTurn(gameState, getPlayerForCurrentTurn))
  }

  private def inTurn(gameState: GameState, playerInTurn: GamePlayer): Receive = {
    case PlayerActionMade(playerId, action) if playerId == playerInTurn.id => {

      this.determineNextState(gameState, playerInTurn, action) match {
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

      stateResult.additionalInformation match {
        case Some(CardDrawnInfo(card)) => playerInTurn.actorRef ! CardDrawn(card)
        case _ => playerInTurn.actorRef ! TurnEnded
      }

      // update the turn
      this.turnManager.nextTurn

      // notify the next player it's his turn
      this.getPlayerForCurrentTurn.actorRef ! PlayerTurn

      //switch the actor behaviour
      context.become(inTurn(stateResult.updatedState, getPlayerForCurrentTurn))

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
    println(this.players.toString())

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

  /**
   * returns the current player
   *
   * @return the current player
   */
  private def getPlayerForCurrentTurn: GamePlayer =
    this.players.find(_.id == turnManager.playerInTurnId).get


  /**
   * Determines the next game state based on the player action
   *
   * @param currentState current state of the game
   * @param playerInTurn player making the action
   * @param playerAction action made my the player
   * @return a state result or a string representing an error
   */
  def determineNextState(currentState: GameState, playerInTurn: GamePlayer, playerAction: PlayerAction): Either[String, StateResult] = {
    playerAction match {
      case DrawCard => {
        val (updateDeck, cardDrawn) = gameManager.draw(currentState.deck)

        // updated player with the new card on his hand
        val updatedState = currentState
          .getPlayer(playerInTurn.id)
          .map(p => currentState.updatePlayer(p.copy(
            hand = p.hand.copy(playerCards = cardDrawn +: p.hand.playerCards))))
          .get.copy(deck = updateDeck)

        Right(StateResult(
          updatedState = updatedState,
          additionalInformation = Some(CardDrawnInfo(cardDrawn))
        ))
      }

      case PlayedMove(updatedHand, updatedBoard) => {
        if (gameManager.validateTurn(updatedBoard, updatedHand)) {
          val updatedState = currentState
            .getPlayer(playerInTurn.id)
            .map(p => currentState.updatePlayer(p.copy(
              hand = updatedHand)))
            .get.copy(board = updatedBoard)

          Right(StateResult(
            updatedState = updatedState,
            additionalInformation = None
          ))

        } else {
          Left("Non valid plays")
        }
      }


      case _ => Left("Non supported action")
    }
  }

  // TODO receive function for disconnection and error events

}
