package it.parttimeteam.view.startup.scenes

import scalafx.stage.Stage

abstract class BaseStartUpFormScene(val parentStage: Stage) extends BaseStartUpScene(parentStage) {

  def showMessage(message: String): Unit

  def hideMessage(): Unit

  def disableButtons(): Unit

  def resetScreen(): Unit
}
