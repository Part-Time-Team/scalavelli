package it.parttimeteam.controller.game

import it.parttimeteam.Board
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{CardCombination, Hand}
import it.parttimeteam.gamestate.{Opponent, PlayerGameState}
import it.parttimeteam.model.game._
import it.parttimeteam.model.startup.GameMatchInformations
import it.parttimeteam.view.game._
import scalafx.application.{JFXApp, Platform}

class GameControllerImpl extends GameController {

  private var gameStage: MachiavelliGamePrimaryStage = _
  private var gameService: GameService = _

  override def start(app: JFXApp, gameInfo: GameMatchInformations): Unit = {
    Platform.runLater({
      gameStage = MachiavelliGamePrimaryStage(this)
      gameStage.initMatch()
      app.stage = gameStage
    })

    this.gameService = new GameServiceImpl(gameInfo, notifyEvent)
    this.gameService.playerReady()
  }

  def notifyEvent(gameEvent: GameEvent): Unit = gameEvent match {

    case StateUpdatedEvent(state: PlayerGameState) => {
      Platform.runLater({
        gameStage.matchReady()
        gameStage.updateState(state)
      })
    }

    case TurnChangedEvent(state, actualPlayerName) => {
      gameStage.updateState(state)
      gameStage.setMessage(s"It's $actualPlayerName turn")
    }

    case YourTurnEvent(state) => {
      gameStage.updateState(state)
      gameStage.notifyInfo("It's your turn")
      gameStage.setMessage("Your turn")
      gameStage.showTimer()
    }

    case _ =>
  }

  override def onViewEvent(viewEvent: GameViewEvent): Unit = viewEvent match {
    // TODO: To be implementedc
    case MakeCombinationViewEvent(cards) =>
    case PreviousStateViewEvent() =>
    case NextStateViewEvent() =>
    case PickCardCombinationViewEvent(cardCombinationIndex) =>
    case EndTurnViewEvent() =>
    case _ =>
  }

  private def getMockState: PlayerGameState = {
    val board: Board = Board(
      List(
        CardCombination(List(Card("A", "♠", "B"), Card("2", "♠", "B"))),
        CardCombination(List(Card("3", "♠", "B"), Card("4", "♠", "B"))),
        CardCombination(List(Card("5", "♠", "B"), Card("6", "♠", "B"), Card("7", "♠", "B")))
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

    hand = hand.addTableCards(Seq(
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

}