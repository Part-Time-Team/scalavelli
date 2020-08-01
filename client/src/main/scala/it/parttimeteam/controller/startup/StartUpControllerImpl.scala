package it.parttimeteam.controller.startup
import it.parttimeteam.view.startup.MachiavelliStartupPrimaryStage
import scalafx.application.JFXApp

class StartUpControllerImpl extends StartUpController {
  override def start(app: JFXApp): Unit = {
    app.stage = MachiavelliStartupPrimaryStage(this)
  }

  override def requestGameWithPlayers(username: String, playersNumber: Int): Unit = {
    System.out.println(s"requestGameWithPlayers $username - $playersNumber")
  }

  override def requestPrivateGame(username: String, code: String): Unit = {
    System.out.println(s"requestPrivateGame $username - $code")
  }

  override def createPrivateGame(username: String, playersNumber: Int): Unit = {
    System.out.println(s"createPrivateGame $username - $playersNumber")

  }


}
