package it.parttimeteam.controller.game
import akka.actor.ActorRef
import it.parttimeteam.view.game.MachiavelliGamePrimaryStage
import scalafx.application.JFXApp

class GameControllerImpl extends GameController {

  override def start(app: JFXApp, gameRef: ActorRef): Unit = {
    val gameStage: MachiavelliGamePrimaryStage = MachiavelliGamePrimaryStage(this)

    app.stage = gameStage
  }
}
