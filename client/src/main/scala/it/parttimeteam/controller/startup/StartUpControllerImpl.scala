package it.parttimeteam.controller.startup

import it.parttimeteam.view.startup.MachiavelliStartupPrimaryStage
import it.parttimeteam.view.{CreatePrivateGameSubmitViewEvent, PrivateGameSubmitViewEvent, PublicGameSubmitViewEvent, ViewEvent}
import scalafx.application.JFXApp

class StartUpControllerImpl extends StartUpController {
  override def start(app: JFXApp): Unit = {
    app.stage = MachiavelliStartupPrimaryStage(this)
  }

  override def onViewEvent(viewEvent: ViewEvent): Unit = viewEvent match {
    case PublicGameSubmitViewEvent(username, playersNumber) => System.out.println(s"PublicGameSubmitViewEvent $username - $playersNumber")
    case PrivateGameSubmitViewEvent(username, code) => System.out.println(s"PrivateGameSubmitViewEvent $username - $code")
    case CreatePrivateGameSubmitViewEvent(username, playersNumber) => System.out.println(s"CreatePrivateGameSubmitViewEvent $username - $playersNumber")
    case _ =>
  }

}
