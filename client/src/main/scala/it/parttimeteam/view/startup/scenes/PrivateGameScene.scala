package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.BaseScene
import it.parttimeteam.view.startup.listeners.PrivateGameSceneListener
import javafx.geometry.Insets
import scalafx.geometry.Pos.{BottomRight, Center}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ComboBox, TextField}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.stage.Stage

class PrivateGameScene(override val parentStage: Stage, val listener: PrivateGameSceneListener, val onBack: () => Unit) extends BaseScene(parentStage) {
  val btnBack: Button = new Button("<")
  val btnSubmit: Button = new Button("Send")

  val usernameField: TextField = new TextField()
  usernameField.setPromptText("Username")
  usernameField.setMaxWidth(400)

  val codeField: TextField = new TextField()
  codeField.setPromptText("Code")
  codeField.setMaxWidth(400)

  val borderPane: BorderPane = new BorderPane()
  borderPane.setPadding(new Insets(20,20,20,20))
  val center: VBox = new VBox()
  center.spacing = 20d
  val bottom: HBox = new HBox()

  center.alignment = Center
  bottom.alignment = BottomRight

  borderPane.center = center
  borderPane.top = btnBack
  borderPane.bottom = bottom

  center.getChildren.addAll(usernameField, codeField)
  bottom.getChildren.add(btnSubmit)

  val alert: Alert = new Alert(AlertType.Warning)
  alert.setTitle("Input missing")
  alert.setHeaderText(null)
  alert.setContentText("You must enter a username and code.")

  btnSubmit.onAction = _ => {
    val username: String = usernameField.getText
    val code: String = codeField.getText

    if (!username.isEmpty && !code.isEmpty) {
      listener.registerToPrivateGame(username, code)
    } else {
      alert.showAndWait()
    }
  }

  btnBack.onAction = _ => onBack()

  root = borderPane
}
