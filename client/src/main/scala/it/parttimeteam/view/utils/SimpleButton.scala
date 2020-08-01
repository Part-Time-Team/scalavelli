package it.parttimeteam.view.utils

import scalafx.scene.control.Button

object SimpleButton {

  def apply(text: String, onClick: () => Unit): Button = {
    val btn: Button = new Button(text)
    btn.onAction = _ => onClick()
    btn
  }

  def apply(text: String, onClick: () => Unit, minWidth: Double): Button = {
    val btn = this (text, onClick)
    btn.setMinWidth(minWidth)
    btn
  }
}
