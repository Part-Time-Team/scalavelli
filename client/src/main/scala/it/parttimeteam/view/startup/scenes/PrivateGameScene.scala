package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.startup.PrivateGameSubmitViewEvent
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
  * Allow to participate to a private game by inserting a private code.
  *
  * @param listener to interact with parent stage
  */
class PrivateGameScene(val parentStage: Stage, val listener: StartupSceneListener) extends StartupFormScene(parentStage) {
  val topBar: StartupSceneTopBar = new StartupSceneTopBarImpl(listener)
  val bottomBar: StartupSceneBottomBar = new StartupSceneBottomBarImpl(() => submit())

  val usernameLabel: Label = ScalavelliLabel(Strings.USERNAME, ViewConfig.formLabelFontSize)
  val usernameField: TextField = ScalavelliTextField(Strings.USERNAME)

  val codeLabel: Label = ScalavelliLabel("Code", ViewConfig.formLabelFontSize)
  val codeField: TextField = ScalavelliTextField("Code")

  val center: VBox = new VBox()
  center.spacing = ViewConfig.formSpacing
  center.maxWidth = ViewConfig.formWidth

  usernameLabel.maxWidth <== center.width
  usernameField.maxWidth <== center.width
  codeLabel.maxWidth <== center.width
  codeField.maxWidth <== center.width

  center.alignment = Center

  center.getChildren.addAll(usernameLabel, usernameField, codeLabel, codeField)

  mainContent.center = center
  mainContent.top = topBar
  mainContent.bottom = bottomBar

  bottomBar.hideLoading()
  bottomBar.hideMessage()

  val alert: Alert = ScalavelliAlert(Strings.INPUT_MISSING_DIALOG_TITLE, Strings.INPUT_MISSING_USER_CODE_DIALOG_MESSAGE, AlertType.Warning, parentStage)


  override def showMessage(message: String): Unit = bottomBar.showMessage(message)

  override def hideMessage(): Unit = bottomBar.hideMessage()

  private def submit(): Unit = {
    val username: String = usernameField.getText
    val code: String = codeField.getText

    if (!username.isEmpty && !code.isEmpty) {
      listener.onSubmit(PrivateGameSubmitViewEvent(username, code))
      bottomBar.showLoading()
      disableActions()
    } else {
      alert.showAndWait()
    }
  }

  override def disableActions(): Unit = {
    bottomBar.disableActions()
    usernameField.setEditable(false)
    codeField.setEditable(false)
  }

  override def enableActions(): Unit = {
    bottomBar.enableActions()
    usernameField.setEditable(true)
    codeField.setEditable(true)
  }

  override def resetScreen(): Unit = {
    usernameField.text = ""
    codeField.text = ""
    usernameField.setEditable(true)
    codeField.setEditable(true)
    bottomBar.resetScreen()
  }
}
