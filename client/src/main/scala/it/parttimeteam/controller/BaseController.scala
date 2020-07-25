package it.parttimeteam.controller

import scalafx.application.JFXApp

trait BaseController {
  def start(app: JFXApp): Unit

}
