package it.parttimeteam.controller.startup
import it.parttimeteam.view.startup.MachiavelliStartupPrimaryStage
import scalafx.application.JFXApp

class StartUpControllerImpl extends StartUpController {
  override def start(app: JFXApp): Unit = {
    app.stage = MachiavelliStartupPrimaryStage(this)
  }

  override def requestGameWithPlayers(): Unit = {
    System.out.println("requestGameWithPlayers")
  }

  override def requestPrivateGame(): Unit = {
    System.out.println("requestPrivateGame")
  }

  override def createPrivateGame(): Unit = {
    System.out.println("createPrivateGame")
  }
}
