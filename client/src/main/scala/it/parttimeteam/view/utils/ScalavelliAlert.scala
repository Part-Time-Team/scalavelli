package it.parttimeteam.view.utils

import it.parttimeteam.core.GameError
import it.parttimeteam.model.ErrorEvent
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

/**
  * Builder for a default Alert dialog
  */
object ScalavelliAlert {

  def apply(title: String, message: String, alertType: AlertType): Alert = {
    val alert: Alert = new Alert(alertType)
    alert.setTitle(title)
    alert.setHeaderText(null)
    alert.setContentText(message)
    alert
  }

  def apply(title: String, error: ErrorEvent, alertType: AlertType): Alert = {
    val alert: Alert = new Alert(alertType)
    alert.setTitle(title)
    alert.setHeaderText(null)
    alert.setContentText(StringParser.parseError(error))
    alert
  }

}
