package it.parttimeteam.view.utils

import scalafx.scene.control.Button

object ButtonFactory {

  def makeButton(text: String, onClick: () => Unit): Button = {
    val btn: Button = new Button(text)
    btn.onAction = _ => onClick()
    btn
  }

  def makeButton(text: String, onClick: () => Unit, minWidth: Double): Button = {
    val btn = makeButton(text, onClick)
    btn.setMinWidth(minWidth)
    btn
  }
}
