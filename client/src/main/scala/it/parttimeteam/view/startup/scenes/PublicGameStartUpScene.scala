package it.parttimeteam.view.startup.scenes

import it.parttimeteam.GamePreferences
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.startup.PublicGameSubmitViewEvent
import it.parttimeteam.view.startup.listeners.StartUpSceneListener
import it.parttimeteam.view.utils.{MachiavelliAlert, MachiavelliLabel, MachiavelliTextField}
import scalafx.geometry.Insets
import scalafx.geometry.Pos.Center
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, VBox}

/**
  * Allow to participate to a game by selecting the number of players to play with.
  *
  * @param listener to interact with parent stage
  */
class PublicGameStartUpScene(val listener: StartUpSceneListener) extends BaseStartUpScene() {
  val topBar: StartUpSceneTopBar = new StartUpSceneTopBar(listener)
  val bottomBar: StartUpSceneBottomBar = new StartUpSceneBottomBar(() => submit())

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

  center.alignment = Center

  val borderPane: BorderPane = new BorderPane()
  borderPane.setPadding(Insets(ViewConfig.screenPadding))

  borderPane.center = center
  borderPane.top = topBar
  borderPane.bottom = bottomBar

  bottomBar.hideLoading()
  bottomBar.hideMessage()

  center.getChildren.addAll(usernameLabel, usernameField, selectPlayersLabel, comboBox)

  val alert: Alert = MachiavelliAlert("Input missing", "You must enter username and select players number.", AlertType.Warning)

  root = borderPane

  override def showMessage(message: String): Unit = bottomBar.showMessage(message)

  override def hideMessage(message: String): Unit = bottomBar.hideMessage()

  private def submit(): Unit = {
    val username: String = usernameField.getText
    val nPlayers: Int = comboBox.getValue

    if (!username.isEmpty && nPlayers >= GamePreferences.MIN_PLAYERS_NUM) {
      listener.onSubmit(PublicGameSubmitViewEvent(username, nPlayers))
      bottomBar.showLoading()
      bottomBar.disableButtons()
    } else {
      alert.showAndWait()
    }
  }
}
