package it.parttimeteam.view.utils

import it.parttimeteam.model.ErrorEvent
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.stage.Stage

/**
  * Builder for a default Alert dialog
  */
object ScalavelliAlert {

  def apply(title: String, message: String, alertType: AlertType, parent: Stage): Alert = {
    val alert: Alert = new Alert(alertType)
    alert.setTitle(title)
    alert.setHeaderText(null)
    alert.setContentText(message)
    //alert.initOwner(parent) // this break dialogs content
    alert
  }

  def apply(title: String, error: ErrorEvent, alertType: AlertType, parent: Stage): Alert = {
    val alert: Alert = new Alert(alertType)
    alert.setTitle(title)
    alert.setHeaderText(null)
    alert.setContentText(StringParser.parseError(error))
    //alert.initOwner(parent) // this break dialogs content
    alert
  }

}
