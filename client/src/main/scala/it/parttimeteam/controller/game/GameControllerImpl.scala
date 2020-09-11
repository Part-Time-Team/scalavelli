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

  def notifyEvent(serverGameEvent: GameEvent): Unit = serverGameEvent match {

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
    }

    case MakeCombinationEvent(cards: Seq[Card]) => gameService.makeCombination(cards)

    case PickCardCombinationEvent(combinationId: String) => gameService.pickCardCombination(combinationId)

    case PickCardsEvent(cards: Seq[Card]) => gameService.pickCardsFromBoard(cards)

    case UpdateCardCombinationEvent(combinationId: String, cards: Seq[Card]) => gameService.updateCardCombination(combinationId, cards)

    case PlayAgainEvent => playAgain()
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

  override def end(): Unit = {

  }
}
