package it.parttimeteam.controller.game

import it.parttimeteam.core.GameInterfaceImpl
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{Board, CardCombination, Hand}
import it.parttimeteam.gamestate.{Opponent, PlayerGameState}
import it.parttimeteam.model.game._
import it.parttimeteam.model.startup.GameMatchInformations
import it.parttimeteam.view.game._
import scalafx.application.{JFXApp, Platform}

class GameControllerImpl(playAgain: () => Unit) extends GameController {

  private var gameStage: MachiavelliGameStage = _
  private var gameService: GameService = _
  private var currentState: ClientGameState = _

  override def start(app: JFXApp, gameInfo: GameMatchInformations): Unit = {
    Platform.runLater({
      gameStage = MachiavelliGameStage(this)
      gameStage.initMatch()
      app.stage = gameStage
    })

    this.gameService = new GameServiceImpl(gameInfo, notifyEvent, new GameInterfaceImpl())
    this.gameService.playerReady()
  }

  def notifyEvent(serverGameEvent: ServerGameEvent): Unit = serverGameEvent match {

    case StateUpdatedEvent(state: ClientGameState) => {
      currentState = state
      Platform.runLater({
        gameStage.matchReady()
        gameStage.updateState(state)
      })
    }

    case OpponentInTurnEvent(actualPlayerName) => {
      gameStage.setMessage(s"It's $actualPlayerName turn")
    }

    case InTurnEvent => {
      gameStage.setInTurn()
      gameStage.showTimer()
    }

    case InfoEvent(message: String) => {
      gameStage.notifyInfo(message)
    }

    case ErrorEvent(reason: String) => {
      gameStage.notifyError(reason)
    }

    case GameWonEvent => {
      gameStage.notifyGameEnd(GameWon)
    }

    case GameLostEvent(winnerName: String) => {
      gameStage.notifyGameEnd(GameLost(winnerName))
    }

    case GameEndedWithErrorEvent(reason: String) => {
      gameStage.notifyGameEnd(GameEndWithError(reason))
    }

    case TurnEndedEvent => {
      gameStage.setTurnEnded()
      gameStage.hideTimer()
    }

    case _ =>
  }

  override def onViewEvent(viewEvent: ViewGameEvent): Unit = viewEvent match {
    case LeaveGameEvent => gameService.notifyUserAction(LeaveGameAction)

    case UndoEvent => gameService.notifyUserAction(UndoAction)

    case RedoEvent => gameService.notifyUserAction(RedoAction)

    case ResetEvent => gameService.notifyUserAction(ResetAction)

    case SortHandBySuitEvent => gameService.notifyUserAction(SortHandBySuitAction)

    case SortHandByRankEvent => gameService.notifyUserAction(SortHandByRankAction)

    case EndTurnEvent => {
      if (currentState.canDrawCard) {
        gameService.notifyUserAction(EndTurnAndDrawAction)
      } else {
        gameService.notifyUserAction(EndTurnAction)
      }
    }

    case MakeCombinationEvent(cards: Seq[Card]) => gameService.notifyUserAction(MakeCombinationAction(cards))

    case PickCardCombinationEvent(combinationId: String) => gameService.notifyUserAction(PickCardCombinationAction(combinationId))

    case PickCardsEvent(cards: Seq[Card]) => gameService.notifyUserAction(PickCardsAction(cards))

    case UpdateCardCombinationEvent(combinationId: String, cards: Seq[Card]) => gameService.notifyUserAction(UpdateCardCombinationAction(combinationId, cards))

    case PlayAgainEvent => playAgain()
  }

  private def getMockState: PlayerGameState = {
    val board: Board = Board(
      List(
        CardCombination("#1", Seq(Card("A", "♠", "B"), Card("2", "♠", "B"))),
        CardCombination("#2", Seq(Card("3", "♠", "B"), Card("4", "♠", "B"))),
        CardCombination("#3", Seq(Card("5", "♠", "B"), Card("6", "♠", "B"), Card("7", "♠", "B")))
      )
    )

    var hand: Hand = Hand()
    hand = hand.addPlayerCards(Seq(
      Card("A", "♥", "B"),
      Card("2", "♥", "B"),
      Card("3", "♥", "B"),
      Card("4", "♥", "B"),
      Card("5", "♥", "B"),
      Card("6", "♥", "B"),
      Card("7", "♥", "B")
    ))

    hand = hand.addBoardCards(Seq(
      Card("K", "♥", "B"),
      Card("Q", "♠", "B")
    ))

    val players: Seq[Opponent] = List(
      Opponent("Matteo", 5),
      Opponent("Daniele", 7),
      Opponent("Lorenzo", 3),
    )

    PlayerGameState(board, hand, players)
  }

  override def end(): Unit = {

  }
}
