package it.parttimeteam.view.utils

import scalafx.scene.control.Button

/**
  * Builder for a default Button
  */
object MachiavelliButton {

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
