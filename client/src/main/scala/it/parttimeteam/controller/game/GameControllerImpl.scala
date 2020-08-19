package it.parttimeteam.controller.game

import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.model.game.{GameService, GameServiceImpl, GameServiceListener}
import it.parttimeteam.model.startup.GameMatchInformations
import it.parttimeteam.view.game.MachiavelliGamePrimaryStage
import scalafx.application.{JFXApp, Platform}

class GameControllerImpl extends GameController {

  private var gameService: GameService = _

  override def start(app: JFXApp, gameInfo: GameMatchInformations): Unit = {
    Platform.runLater({
      this.gameService = new GameServiceImpl(gameInfo, new GameServiceListener {
        override def onGameStateUpdated(state: PlayerGameState): Unit = ???
      })
      val gameStage: MachiavelliGamePrimaryStage = MachiavelliGamePrimaryStage(this)
      app.stage = gameStage
      this.gameService.playerReady()
    })
  }
}
