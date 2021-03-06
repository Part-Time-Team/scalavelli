package it.parttimeteam.controller.game

import java.util.concurrent.TimeUnit

import it.parttimeteam.Constants
import it.parttimeteam.controller.ViewMessage
import it.parttimeteam.controller.game.TurnTimer.TurnTimerImpl
import it.parttimeteam.core.GameInterfaceImpl
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{Board, CardCombination, Hand}
import it.parttimeteam.gamestate.{Opponent, PlayerGameState}
import it.parttimeteam.model.ErrorEvent
import it.parttimeteam.model.game._
import it.parttimeteam.model.startup.GameMatchInformations
import it.parttimeteam.view.game._
import scalafx.application.{JFXApp, Platform}

class GameControllerImpl(playAgain: () => Unit) extends GameController {

  private var gameStage: GameStage = _
  private var gameService: GameService = _
  private var currentState: ClientGameState = _

  private val turnTimer: TurnTimer = new TurnTimerImpl(Constants.Client.TURN_TIMER_DURATION, new TurnTimerListener {

    override def onStart(): Unit = {
      val time = millisToMinutesAndSeconds(Constants.Client.TURN_TIMER_DURATION * 1000)
      gameStage.showTimer(time._1, time._2)
    }

    override def onEnd(): Unit = {
      gameStage.notifyTimerEnded()
      gameService.endTurnDrawingACard()
    }

    override def onTick(millis: Long): Unit = {
      val time = millisToMinutesAndSeconds(millis)
      gameStage.updateTimer(time._1, time._2)
    }
  })

  override def onViewEvent(viewEvent: ViewGameEvent): Unit = viewEvent match {
    case LeaveGameEvent => gameService.leaveGame()

    case UndoEvent => gameService.undoTurn()

    case RedoEvent => gameService.redoTurn()

    case ResetEvent => gameService.resetTurnState()

    case SortHandBySuitEvent => gameService.sortHandBySuit()

    case SortHandByRankEvent => gameService.sortHandByRank()

    case EndTurnEvent => {
      if (currentState.canDrawCard) {
        gameService.endTurnDrawingACard()
      } else {
        gameService.endTurnWithMoves()
      }
      turnTimer.end()
    }

    case MakeCombinationEvent(cards: Seq[Card]) => gameService.makeCombination(cards)

    case PickCardCombinationEvent(combinationId: String) => gameService.pickCardCombination(combinationId)

    case PickCardsEvent(cards: Seq[Card]) => gameService.pickCardsFromBoard(cards)

    case UpdateCardCombinationEvent(combinationId: String, cards: Seq[Card]) => gameService.updateCardCombination(combinationId, cards)

    case PlayAgainEvent => playAgain()

    case TurnStartedEvent => turnTimer.start()
  }

  override def start(app: JFXApp, gameInfo: GameMatchInformations): Unit = {
    Platform.runLater({
      gameStage = GameStage(this)
      gameStage.initMatch()
      app.stage = gameStage
    })

    this.gameService = new GameServiceImpl(gameInfo, notifyEvent, new GameInterfaceImpl())
    this.gameService.playerReady()
  }

  override def end(): Unit = {

  }

  private def notifyEvent(serverGameEvent: GameEvent): Unit = serverGameEvent match {

    case StateUpdatedEvent(state: ClientGameState) => {
      currentState = state
      Platform.runLater({
        gameStage.matchReady()
        gameStage.updateState(state)
      })
    }

    case OpponentInTurnEvent(actualPlayerName) => gameStage.setMessage(ViewMessage.ActualPlayerTurn(actualPlayerName))

    case InTurnEvent => gameStage.setInTurn()

    case InfoEvent(message: String) => gameStage.notifyInfo(message)

    case GameErrorEvent(error: ErrorEvent) => gameStage.notifyError(error)

    case GameWonEvent => gameStage.notifyGameEnd(GameWon)

    case GameLostEvent(winnerName: String) => gameStage.notifyGameEnd(GameLost(winnerName))

    case GameEndedBecausePlayerLeft => gameStage.notifyGameEnd(GameEndPlayerLeft)

    case TurnEndedEvent => gameStage.setTurnEnded()

    case _ =>
  }

  private def millisToMinutesAndSeconds(millis: Long): (Long, Long) = {
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(millis)
    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes)
    (minutes, seconds)
  }

  private def getMockState: ClientGameState = {
    val board: Board = Board(
      List(
        CardCombination("#1", Seq(Card("1", "S", "B"), Card("2", "S", "B"))),
        CardCombination("#2", Seq(Card("3", "S", "B"), Card("4", "S", "B"))),
        CardCombination("#3", Seq(Card("5", "S", "B"), Card("6", "S", "B"), Card("7", "S", "B")))
      )
    )

    var hand: Hand = Hand()
    hand = hand.addPlayerCards(Seq(
      Card("1", "H", "B"),
      Card("2", "H", "B"),
      Card("3", "H", "B"),
      Card("4", "H", "B"),
      Card("5", "H", "B"),
      Card("6", "H", "B"),
      Card("7", "H", "B")
    ))

    hand = hand.addBoardCards(Seq(
      Card("13", "H", "B"),
      Card("12", "S", "B")
    ))

    val players: Seq[Opponent] = List(
      Opponent("Matteo", 5),
      Opponent("Daniele", 7),
      Opponent("Lorenzo", 3),
    )

    ClientGameState(PlayerGameState(board, hand, players), true, true, true, true)
  }
}
