package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.BaseScene
import it.parttimeteam.view.startup.listeners.CreatePrivateGameSceneListener
import it.parttimeteam.view.utils.{AlertFactory, ButtonFactory}
import javafx.geometry.Insets
import scalafx.geometry.Pos.{BottomRight, Center}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ComboBox, TextField}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.stage.Stage

class CreatePrivateGameScene(override val parentStage: Stage, val listener: CreatePrivateGameSceneListener, val onBack: () => Unit) extends BaseScene(parentStage) {
  val btnBack: Button = ButtonFactory.makeButton("<", onBack)
  val btnSubmit: Button = ButtonFactory.makeButton("Send", submit)

  val usernameField: TextField = new TextField()
  usernameField.setPromptText("Username")
  usernameField.setMaxWidth(400)

  val options: Range = 2 to 6 by 1

  val comboBox = new ComboBox(options)
  comboBox.setPrefWidth(400)

  val borderPane: BorderPane = new BorderPane()
  borderPane.setPadding(new Insets(20, 20, 20, 20))
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


  val alert: Alert = AlertFactory.makeAlert("Input missing", "You must enter username and select players number.", AlertType.Warning)

  root = borderPane

  def submit: () => Unit = _ => {
    val username: String = usernameField.getText
    val nPlayers: Int = comboBox.getValue

    if (!username.isEmpty && nPlayers > 2) {
      listener.createPrivateGame(username, nPlayers)
    } else {
      alert.showAndWait()
    }
  }
}
