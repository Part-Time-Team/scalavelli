package it.parttimeteam.view

import it.parttimeteam.view.startup.MachiavelliStartupStage
import scalafx.application.JFXApp

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

class ViewImpl(private val app: JFXApp) extends View {

  /** Implicit executor */
  implicit val executionContextExecutor: ExecutionContextExecutor = ExecutionContext.global

  /** Setting the primary stage */
  app.stage = MachiavelliStartupStage()
}