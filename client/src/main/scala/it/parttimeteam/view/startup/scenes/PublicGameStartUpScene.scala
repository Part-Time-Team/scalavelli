package it.parttimeteam.view.startup.scenes

import it.parttimeteam.GamePreferences
import it.parttimeteam.view.startup.listeners.StartUpSceneListener
import it.parttimeteam.view.utils.{MachiavelliAlert, MachiavelliButton, MachiavelliLabel, MachiavelliTextField}
import it.parttimeteam.view.{PublicGameSubmitViewEvent, ViewConfig}
import javafx.scene.control
import scalafx.geometry.Insets
import scalafx.geometry.Pos.{BottomRight, Center}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox, VBox}

/**
  * Allow to participate to a game by selecting the number of players to play with.
  *
  * @param listener to interact with parent stage
  */
class PublicGameStartUpScene(val listener: StartUpSceneListener) extends BaseStartUpScene() {
  val btnBack: Button = MachiavelliButton("<", listener.onBackPressed)
  val btnSubmit: Button = MachiavelliButton("Send", submit)

  val usernameLabel: Label = MachiavelliLabel("Username", ViewConfig.formLabelFontSize)
  val usernameField: TextField = MachiavelliTextField("Username")

  val options: Range = GamePreferences.MIN_PLAYERS_NUM to GamePreferences.MAX_PLAYERS_NUM by 1

  val selectPlayersLabel: Label = MachiavelliLabel("Select players number", ViewConfig.formLabelFontSize)
  val comboBox = new ComboBox(options)
  comboBox.setValue(GamePreferences.MIN_PLAYERS_NUM)

  val center: VBox = new VBox()
  center.spacing = ViewConfig.formSpacing
  center.maxWidth = ViewConfig.formWidth

  selectPlayersLabel.maxWidth <== center.width
  usernameLabel.maxWidth <== center.width

  comboBox.maxWidth <== center.width

  val bottom: HBox = new HBox()

  center.alignment = Center
  bottom.alignment = BottomRight

  val borderPane: BorderPane = new BorderPane()
  borderPane.setPadding(Insets(ViewConfig.screenPadding))
  borderPane.center = center
  borderPane.top = btnBack
  borderPane.bottom = bottom

  override val progress: ProgressIndicator = new ProgressIndicator()
  progress.prefHeight <== bottom.height
  hideLoading()

  override val messageContainer: Label = MachiavelliLabel()
  hideMessage()

  center.getChildren.addAll(usernameLabel, usernameField, selectPlayersLabel, comboBox)
  bottom.getChildren.addAll(messageContainer, progress, btnSubmit)

  val alert: Alert = MachiavelliAlert("Input missing", "You must enter username and select players number.", AlertType.Warning)

  root = borderPane

  def submit(): Unit = {
    val username: String = usernameField.getText
    val nPlayers: Int = comboBox.getValue

    if (!username.isEmpty && nPlayers >= GamePreferences.MIN_PLAYERS_NUM) {
      listener.onSubmit(PublicGameSubmitViewEvent(username, nPlayers))
      showLoading()
      btnSubmit.setDisable(true)
    } else {
      alert.showAndWait()
    }
  }

}
