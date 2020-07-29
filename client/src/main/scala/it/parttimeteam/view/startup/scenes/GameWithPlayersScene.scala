package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.BaseScene
import it.parttimeteam.view.startup.listeners.GameWithPlayersSceneListener
import javafx.geometry.Insets
import scalafx.geometry.Pos.{BottomRight, Center}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ComboBox, TextField}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.stage.Stage

class GameWithPlayersScene(override val parentStage: Stage, val listener: GameWithPlayersSceneListener, val onBack: () => Unit) extends BaseScene(parentStage) {
  val btnBack: Button = new Button("<")
  val btnSubmit: Button = new Button("Send")

  val usernameField: TextField = new TextField()
  usernameField.setPromptText("Username")
  usernameField.setMaxWidth(400)

  val options: Range = 2 to 6 by 1

  val comboBox = new ComboBox(options)
  comboBox.setPrefWidth(400)

  val borderPane: BorderPane = new BorderPane()
  borderPane.setPadding(new Insets(20,20,20,20))
  val center: VBox = new VBox()
  center.spacing = 10d
  val bottom: HBox = new HBox()

  center.alignment = Center
  bottom.alignment = BottomRight

  borderPane.center = center
  borderPane.top = btnBack
  borderPane.bottom = bottom

  center.getChildren.addAll(usernameField, comboBox)
  bottom.getChildren.add(btnSubmit)

  val alert: Alert = new Alert(AlertType.Warning)
  alert.setTitle("Input missing")
  alert.setHeaderText(null)
  alert.setContentText("You must enter a username and select players number.")

  btnSubmit.onAction = _ => {
    val username: String = usernameField.getText
    val nPlayers: Int = comboBox.getValue

    if (!username.isEmpty && nPlayers > 2) {
      listener.registerToGame(username, nPlayers)
    } else {
      alert.showAndWait()
    }
  }

  btnBack.onAction = _ => onBack()

  root = borderPane
}
