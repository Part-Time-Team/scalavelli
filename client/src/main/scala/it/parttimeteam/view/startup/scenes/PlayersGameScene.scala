package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.BaseScene
import it.parttimeteam.view.startup.listeners.PlayersGameSceneListener
import it.parttimeteam.view.utils.{AlertFactory, ButtonFactory}
import scalafx.geometry.Insets
import scalafx.geometry.Pos.{BottomRight, Center}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.stage.Stage

class PlayersGameScene(override val parentStage: Stage, val listener: PlayersGameSceneListener, val onBack: () => Unit) extends BaseScene(parentStage) {
  val btnBack: Button = ButtonFactory.makeButton("<", onBack)
  val btnSubmit: Button = ButtonFactory.makeButton("Send", submit)

  val usernameField: TextField = new TextField()
  usernameField.setPromptText("Username")
  usernameField.setMaxWidth(400)

  val options: Range = 2 to 6 by 1

  val selectPlayersLabel: Label = new Label("Select players number")
  selectPlayersLabel.setPrefWidth(400)
  val comboBox = new ComboBox(options)
  comboBox.setPrefWidth(400)
  comboBox.setValue(4)

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

  center.getChildren.addAll(usernameField, selectPlayersLabel, comboBox)
  bottom.getChildren.addAll(progress, btnSubmit)

  val alert: Alert = AlertFactory.makeAlert("Input missing", "You must enter username and select players number.", AlertType.Warning)

  root = borderPane

  def submit(): Unit = {
    val username: String = usernameField.getText
    val nPlayers: Int = comboBox.getValue

    if (!username.isEmpty && nPlayers > 2) {
      listener.registerToGame(username, nPlayers)
      showLoading()
      btnSubmit.setDisable(true)
    } else {
      alert.showAndWait()
    }
  }
}
