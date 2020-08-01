package it.parttimeteam.view.utils

import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

object BasicAlert {
  def apply(title: String, message: String, alertType: AlertType): Alert = {
    val alert: Alert = new Alert(alertType)
    alert.setTitle(title)
    alert.setHeaderText(null)
    alert.setContentText(message)
    alert
  }
}
