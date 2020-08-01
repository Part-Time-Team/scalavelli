package it.parttimeteam.view

import scalafx.application.JFXApp

/**
  * The main View of the application.
  */
trait View {
  def apply(app: JFXApp): View = new ViewImpl(app)
}




