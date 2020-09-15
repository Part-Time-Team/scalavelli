package it.parttimeteam.view.startup.scenes

import it.parttimeteam.GamePreferences
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.startup.CreatePrivateGameSubmitViewEvent
import it.parttimeteam.view.startup.listeners.StartupSceneListener
import it.parttimeteam.view.startup.scenes.StartupSceneBottomBar.StartupSceneBottomBarImpl
import it.parttimeteam.view.startup.scenes.StartupSceneTopBar.StartupSceneTopBarImpl
import it.parttimeteam.view.utils.{ScalavelliAlert, ScalavelliLabel, ScalavelliTextField}
import javafx.scene.text.Font
import scalafx.geometry.Pos.Center
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.VBox
import scalafx.stage.Stage

/**
  * Allow to participate to create a private game by inserting the number of players.
  * Obtains a private code to share to invite other players.
  **/
trait CreatePrivateGameScene {
  def showCode(code: String): Unit
}


/**
  * Allow to participate to create a private game by inserting the number of players.
  * Obtains a private code to share to invite other players.
  *
  * @param listener to interact with parent stage
  */
class CreatePrivateGameStartupSceneImpl(val parentStage: Stage, val listener: StartupSceneListener) extends StartupFormScene(parentStage) with CreatePrivateGameScene {
  val topBar: StartupSceneTopBar = new StartupSceneTopBarImpl(listener)
  val bottomBar: StartupSceneBottomBar = new StartupSceneBottomBarImpl(() => submit())

  val usernameLabel: Label = ScalavelliLabel("Username", ViewConfig.formLabelFontSize)
  val usernameField: TextField = ScalavelliTextField("Username")

  val options: Range = GamePreferences.MIN_PLAYERS_NUM to GamePreferences.MAX_PLAYERS_NUM by 1

  val selectPlayersLabel: Label = ScalavelliLabel("Select players number", ViewConfig.formLabelFontSize)
  val comboBox = new ComboBox(options)
  comboBox.setValue(GamePreferences.MIN_PLAYERS_NUM)

  val center: VBox = new VBox()
  center.spacing = ViewConfig.formSpacing
  center.maxWidth = ViewConfig.formWidth

  usernameLabel.maxWidth <== center.width
  usernameField.maxWidth <== center.width
  selectPlayersLabel.maxWidth <== center.width
  comboBox.maxWidth <== center.width

  center.alignment = Center

  mainContent.center = center
  mainContent.top = topBar
  mainContent.bottom = bottomBar

  val codeContainer: VBox = new VBox()
  val codeLabel: Label = ScalavelliLabel("Here is your code")
  val codeValue: Label = ScalavelliLabel(ViewConfig.titleFontSize)
  codeValue.setFont(new Font(100))
  codeContainer.getChildren.addAll(codeLabel, codeValue)
  codeContainer.setVisible(false)

  bottomBar.hideLoading()
  bottomBar.hideMessage()

  center.getChildren.addAll(usernameLabel, usernameField, selectPlayersLabel, comboBox, codeContainer)

  val alert: Alert = ScalavelliAlert("Input missing", "You must enter username and select players number.", AlertType.Warning)

  override def showMessage(message: String): Unit = bottomBar.showMessage(message)

  override def hideMessage(): Unit = bottomBar.hideMessage()

  override def showCode(code: String): Unit = {
    codeContainer.setVisible(true)
    codeValue.setText(code)
  }

  override def disableActions(): Unit = {
    usernameField.setEditable(false)
    comboBox.setDisable(true)
    bottomBar.disableActions()
  }

  override def enableActions(): Unit = {
    usernameField.setEditable(true)
    comboBox.setDisable(false)
    bottomBar.enableActions()
  }

  override def resetScreen(): Unit = {
    usernameField.text = ""
    usernameField.setEditable(true)
    codeValue.text = ""
    codeContainer.setVisible(false)
    comboBox.setDisable(false)
    bottomBar.resetScreen()
  }

  private def submit(): Unit = {
    val username: String = usernameField.getText
    val nPlayers: Int = comboBox.getValue

    if (!username.isEmpty && nPlayers >= GamePreferences.MIN_PLAYERS_NUM) {
      listener.onSubmit(CreatePrivateGameSubmitViewEvent(username, nPlayers))
      bottomBar.showLoading()
      disableActions()
    } else {
      alert.showAndWait()
    }
  }
}
