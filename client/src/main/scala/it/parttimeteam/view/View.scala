package it.parttimeteam.view

import it.parttimeteam.view.startup.MachiavelliStartupPrimaryStage
import scalafx.application.JFXApp

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

trait View {
  //def setController(controller: BaseController)

}

object View {
  def apply(app: JFXApp): View = new ViewImpl(app)

  class ViewImpl(private val app: JFXApp) extends View {

    /** Implicit executor */
    implicit val executionContextExecutor: ExecutionContextExecutor = ExecutionContext.global

    /** Setting the primary stage */
    app.stage = MachiavelliStartupPrimaryStage()
  }

}




