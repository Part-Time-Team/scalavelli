package it.parttimeteam.view

import it.parttimeteam.view.startup.MachiavelliStartupPrimaryStage
import scalafx.application.JFXApp

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

/**
  * The main View of the application.
  */
trait View {
  def apply(app: JFXApp): View = new ViewImpl(app)
}




