package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.startup.listeners.StartUpSceneListener
import it.parttimeteam.view.utils.{BasicAlert, SimpleButton}
import it.parttimeteam.view.{BaseScene, CreatePrivateGameSubmitViewEvent}
import scalafx.geometry.Insets
import scalafx.geometry.Pos.{BottomRight, Center}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox, VBox}

/**
  * Allow to participate to create a private game by inserting the number of players.
  * Obtains a private code to share to invite other players.
  *
  * @param listener to interact with parent stage
  */
class CreatePrivateGameScene(val listener: StartUpSceneListener) extends BaseScene() {
  val btnBack: Button = SimpleButton("<", listener.onBackPressed)
  val btnSubmit: Button = SimpleButton("Send", submit)

  val usernameField: TextField = new TextField()
  usernameField.setPromptText("Username")
  usernameField.setMaxWidth(400)

  val options: Range = 2 to 6 by 1

  val comboBox = new ComboBox(options)
  comboBox.setPrefWidth(400)

  val borderPane: BorderPane = new BorderPane()
  borderPane.setPadding(Insets(20, 20, 20, 20))
  val center: VBox = new VBox()
  center.spacing = 10d
  val bottom: HBox = new HBox()

  center.alignment = Center
  bottom.alignment = BottomRight

  borderPane.center = center
  borderPane.top = btnBack
  borderPane.bottom = bottom

  override val progress: ProgressIndicator = new ProgressIndicator()
  progress.prefHeight <== bottom.height
  hideLoading()

  center.getChildren.addAll(usernameField, comboBox)
  bottom.getChildren.addAll(progress, btnSubmit)


  val alert: Alert = BasicAlert("Input missing", "You must enter username and select players number.", AlertType.Warning)

  root = borderPane

  def submit(): Unit = {
    val username: String = usernameField.getText
    val nPlayers: Int = comboBox.getValue

    if (!username.isEmpty && nPlayers > 2) {
      listener.onSubmit(CreatePrivateGameSubmitViewEvent(username, nPlayers))
      showLoading()
      btnSubmit.setDisable(true)
    } else {
      alert.showAndWait()
    }
  }
}
