package it.parttimeteam.view.startup.scenes

import it.parttimeteam.GamePreferences
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.startup.PublicGameSubmitViewEvent
import it.parttimeteam.view.startup.listeners.StartUpSceneListener
import it.parttimeteam.view.utils.{MachiavelliAlert, MachiavelliLabel, MachiavelliTextField}
import scalafx.geometry.Pos.Center
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.VBox
import scalafx.stage.Stage

/**
  * Allow to participate to a game by selecting the number of players to play with.
  *
  * @param listener to interact with parent stage
  */
class PublicGameStartUpScene(override val parentStage: Stage, val listener: StartUpSceneListener) extends BaseStartUpFormScene(parentStage) {
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

  mainContent.center = center
  mainContent.top = topBar
  mainContent.bottom = bottomBar

  bottomBar.hideLoading()
  bottomBar.hideMessage()

  center.getChildren.addAll(usernameLabel, usernameField, selectPlayersLabel, comboBox)

  val alert: Alert = MachiavelliAlert("Input missing", "You must enter username and select players number.", AlertType.Warning)

  override def showMessage(message: String): Unit = bottomBar.showMessage(message)

  override def hideMessage(): Unit = bottomBar.hideMessage()

  private def submit(): Unit = {
    val username: String = usernameField.getText
    val nPlayers: Int = comboBox.getValue

    if (!username.isEmpty && nPlayers >= GamePreferences.MIN_PLAYERS_NUM) {
      listener.onSubmit(PublicGameSubmitViewEvent(username, nPlayers))
      bottomBar.showLoading()
      disableButtons()
    } else {
      alert.showAndWait()
    }
  }

  override def disableButtons(): Unit = {
    bottomBar.disableButtons()
    usernameField.setEditable(false)
    comboBox.setDisable(true)
  }

  override def resetScreen(): Unit = {
    usernameField.setEditable(true)
    comboBox.setDisable(false)
    usernameField.text = ""
    bottomBar.reset()
  }
}
