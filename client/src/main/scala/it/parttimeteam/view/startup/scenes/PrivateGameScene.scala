package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.BaseScene
import it.parttimeteam.view.startup.listeners.PrivateGameSceneListener
import it.parttimeteam.view.utils.{AlertFactory, ButtonFactory}
import javafx.geometry.Insets
import scalafx.geometry.Pos.{BottomRight, Center}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, TextField}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.stage.Stage

class PrivateGameScene(override val parentStage: Stage, val listener: PrivateGameSceneListener, val onBack: () => Unit) extends BaseScene(parentStage) {
  val btnBack: Button = ButtonFactory.makeButton("<", onBack)
  val btnSubmit: Button = ButtonFactory.makeButton("Send", submit)

  val usernameField: TextField = new TextField()
  usernameField.setPromptText("Username")
  usernameField.setMaxWidth(400)

  val codeField: TextField = new TextField()
  codeField.setPromptText("Code")
  codeField.setMaxWidth(400)

  val borderPane: BorderPane = new BorderPane()
  borderPane.setPadding(new Insets(20, 20, 20, 20))
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

  val alert: Alert = AlertFactory.makeAlert("Input missing", "You must enter username and code.", AlertType.Warning)

  root = borderPane

  def submit: () => Unit = _ => {
    val username: String = usernameField.getText
    val code: String = codeField.getText

    if (!username.isEmpty && !code.isEmpty) {
      listener.registerToPrivateGame(username, code)
    } else {
      alert.showAndWait()
    }
  }
}
