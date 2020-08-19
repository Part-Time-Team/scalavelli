package it.parttimeteam.controller.game

import it.parttimeteam.Board
import it.parttimeteam.core.cards.{Card, Color}
import it.parttimeteam.core.collections.{CardCombination, Hand}
import it.parttimeteam.gamestate.{Opponent, PlayerGameState}
import it.parttimeteam.model.game.{GameService, GameServiceImpl, GameServiceListener}
import it.parttimeteam.model.startup.GameMatchInformations
import it.parttimeteam.view.{GameStartedViewEvent, ViewEvent}
import it.parttimeteam.view.game.MachiavelliGamePrimaryStage
import scalafx.application.{JFXApp, Platform}

class GameControllerImpl extends GameController {
  var gameStage: MachiavelliGamePrimaryStage = _

  private var gameService: GameService = _

  override def start(app: JFXApp, gameInfo: GameMatchInformations): Unit = {
    Platform.runLater({
      val gameStage: MachiavelliGamePrimaryStage = MachiavelliGamePrimaryStage(this)

      gameStage.initMatch()

      this.gameService = new GameServiceImpl(gameInfo, new GameServiceListener {
        override def onGameStateUpdated(state: PlayerGameState): Unit = {
          gameStage.matchReady()
          gameStage.updateState(state)
        }
      })

      app.stage = gameStage
      this.gameService.playerReady()

      // TODO: remove when server works
      gameStage.matchReady()
      gameStage.updateState(getMockState)
    })
  }

  override def onViewEvent(viewEvent: ViewEvent): Unit = viewEvent match {
    case GameStartedViewEvent(initialState: PlayerGameState) => {
      gameStage.matchReady()
      gameStage.updateState(initialState)
    }
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

    val pippo: Seq[Opponent] = List(
      Opponent("Matteo", 5),
      Opponent("Daniele", 7),
      Opponent("Lorenzo", 3),
    )

    PlayerGameState(board, hand, pippo)
  }
}
