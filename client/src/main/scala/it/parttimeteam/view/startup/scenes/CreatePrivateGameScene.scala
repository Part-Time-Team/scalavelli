package it.parttimeteam.view.startup.scenes

import it.parttimeteam.Constants
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.startup.CreatePrivateGameSubmitViewEvent
import it.parttimeteam.view.startup.listeners.StartupSceneListener
import it.parttimeteam.view.startup.scenes.StartupSceneBottomBar.StartupSceneBottomBarImpl
import it.parttimeteam.view.startup.scenes.StartupSceneTopBar.StartupSceneTopBarImpl
import it.parttimeteam.view.utils.{ScalavelliAlert, ScalavelliLabel, ScalavelliTextField, Strings}
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

  val usernameLabel: Label = ScalavelliLabel(Strings.USERNAME, ViewConfig.FORM_LABEL_FONT_SIZE)
  val usernameField: TextField = ScalavelliTextField(Strings.USERNAME)

  val options: Range = Constants.Client.MIN_PLAYERS_NUM to Constants.Client.MAX_PLAYERS_NUM by 1

  val selectPlayersLabel: Label = ScalavelliLabel(Strings.SELECT_PLAYERS_NUM, ViewConfig.FORM_LABEL_FONT_SIZE)
  val comboBox = new ComboBox(options)
  comboBox.setValue(Constants.Client.MIN_PLAYERS_NUM)

  val center: VBox = new VBox()
  center.spacing = ViewConfig.FORM_SPACING
  center.maxWidth = ViewConfig.FORM_WIDTH

  usernameLabel.maxWidth <== center.width
  usernameField.maxWidth <== center.width
  selectPlayersLabel.maxWidth <== center.width
  comboBox.maxWidth <== center.width

  center.alignment = Center

  mainContent.center = center
  mainContent.top = topBar
  mainContent.bottom = bottomBar

  val codeContainer: VBox = new VBox()
  val codeLabel: Label = ScalavelliLabel(Strings.HERE_IS_YOUR_CODE)
  val codeValue: Label = ScalavelliLabel(ViewConfig.TITLE_FONT_SIZE)
  codeValue.setFont(new Font(ViewConfig.CODE_FONT_SIZE))
  codeContainer.getChildren.addAll(codeLabel, codeValue)
  codeContainer.setVisible(false)

  bottomBar.hideLoading()
  bottomBar.hideMessage()

  center.getChildren.addAll(usernameLabel, usernameField, selectPlayersLabel, comboBox, codeContainer)

  val alert: Alert = ScalavelliAlert(Strings.INPUT_MISSING_DIALOG_TITLE, Strings.INPUT_MISSING_USER_NUM_DIALOG_MESSAGE, AlertType.Warning, parentStage)

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

    if (!username.isEmpty && nPlayers >= Constants.Client.MIN_PLAYERS_NUM) {
      listener.onSubmit(CreatePrivateGameSubmitViewEvent(username, nPlayers))
      bottomBar.showLoading()
      disableActions()
    } else {
      alert.showAndWait()
    }
  }
}
