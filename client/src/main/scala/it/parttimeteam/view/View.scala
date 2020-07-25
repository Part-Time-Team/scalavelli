package it.parttimeteam.view
import it.parttimeteam.controller.startup.StartupStageToControllerListener
import it.parttimeteam.view.startup.MachiavelliStartupPrimaryStage
import scalafx.application.JFXApp

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

trait View {
  //def setController(controller: BaseController)

}

object View {
  def apply(app: JFXApp): View = new ViewImpl(app)

  class ViewImpl(private val app: JFXApp) extends View with StartupStageToControllerListener {

    /** Implicit executor */
    implicit val executionContextExecutor: ExecutionContextExecutor = ExecutionContext.global

    /** Setting the primary stage */
    app.stage = MachiavelliStartupPrimaryStage(this)

    override def requestGameWithPlayers(): Unit = ???

    override def requestPrivateGame(): Unit = ???

    override def createPrivateGame(): Unit = ???
  }
}




