package it.parttimeteam.view.startup.scenes

import it.parttimeteam.Constants
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.startup.PublicGameSubmitViewEvent
import it.parttimeteam.view.startup.listeners.StartupSceneListener
import it.parttimeteam.view.startup.scenes.StartupSceneBottomBar.StartupSceneBottomBarImpl
import it.parttimeteam.view.startup.scenes.StartupSceneTopBar.StartupSceneTopBarImpl
import it.parttimeteam.view.utils.{ScalavelliAlert, ScalavelliLabel, ScalavelliTextField, Strings}
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
class PublicGameScene(val parentStage: Stage, val listener: StartupSceneListener) extends StartupFormScene(parentStage) {
  val topBar: StartupSceneTopBar = new StartupSceneTopBarImpl(listener)
  val bottomBar: StartupSceneBottomBar = new StartupSceneBottomBarImpl(() => submit())

  val usernameLabel: Label = ScalavelliLabel(Strings.USERNAME, ViewConfig.FORM_LABEL_FONT_SIZE)
  val usernameField: TextField = ScalavelliTextField(Strings.USERNAME)

  val options: Range = Constants.Client.MIN_PLAYERS_NUM to Constants.Client.MAX_PLAYERS_NUM by 1

  val selectPlayersLabel: Label = ScalavelliLabel(Strings.SELECT_PLAYERS_NUM, ViewConfig.FORM_LABEL_FONT_SIZE)
  val comboBox = new ComboBox(options)
  comboBox.setValue(Constants.Client.MIN_PLAYERS_NUM)

  val center: VBox = new VBox()
  center.spacing = ViewConfig.FORM_SPACING
  center.maxWidth = ViewConfig.FORM_WIDTH

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

  val alert: Alert = ScalavelliAlert(Strings.INPUT_MISSING_DIALOG_TITLE, Strings.INPUT_MISSING_USER_NUM_DIALOG_MESSAGE, AlertType.Warning, parentStage)

  override def showMessage(message: String): Unit = bottomBar.showMessage(message)

  override def hideMessage(): Unit = bottomBar.hideMessage()

  private def submit(): Unit = {
    val username: String = usernameField.getText
    val nPlayers: Int = comboBox.getValue

    if (!username.isEmpty && nPlayers >= Constants.Client.MIN_PLAYERS_NUM) {
      listener.onSubmit(PublicGameSubmitViewEvent(username, nPlayers))
      bottomBar.showLoading()
      disableActions()
    } else {
      alert.showAndWait()
    }
  }

  override def disableActions(): Unit = {
    bottomBar.disableActions()
    usernameField.setEditable(false)
    comboBox.setDisable(true)
  }

  override def enableActions(): Unit = {
    bottomBar.enableActions()
    usernameField.setEditable(true)
    comboBox.setDisable(false)
  }

  override def resetScreen(): Unit = {
    usernameField.setEditable(true)
    comboBox.setDisable(false)
    usernameField.text = ""
    bottomBar.resetScreen()
  }
}
