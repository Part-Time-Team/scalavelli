package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.startup.PrivateGameSubmitViewEvent
import it.parttimeteam.view.startup.listeners.StartUpSceneListener
import it.parttimeteam.view.utils.{MachiavelliAlert, MachiavelliLabel, MachiavelliTextField}
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
class PrivateGameStartUpScene(override val parentStage: Stage, val listener: StartUpSceneListener) extends BaseStartUpFormScene(parentStage) {
  val topBar: StartUpSceneTopBar = new StartUpSceneTopBar(listener)
  val bottomBar: StartUpSceneBottomBar = new StartUpSceneBottomBar(() => submit())

  val usernameLabel: Label = MachiavelliLabel("Username", ViewConfig.formLabelFontSize)
  val usernameField: TextField = MachiavelliTextField("Username")

  val codeLabel: Label = MachiavelliLabel("Code", ViewConfig.formLabelFontSize)
  val codeField: TextField = MachiavelliTextField("Code")

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

  val alert: Alert = MachiavelliAlert("Input missing", "You must enter username and code.", AlertType.Warning)

  override def showMessage(message: String): Unit = bottomBar.showMessage(message)

  override def hideMessage(): Unit = bottomBar.hideMessage()

  private def submit(): Unit = {
    val username: String = usernameField.getText
    val code: String = codeField.getText

    if (!username.isEmpty && !code.isEmpty) {
      listener.onSubmit(PrivateGameSubmitViewEvent(username, code))
      bottomBar.showLoading()
      disableButtons()
    } else {
      alert.showAndWait()
    }
  }

  override def disableButtons(): Unit = {
    bottomBar.disableButtons()
    usernameField.setEditable(false)
    codeField.setEditable(false)
  }

  override def resetScreen(): Unit = {
    usernameField.text = ""
    codeField.text = ""
    usernameField.setEditable(true)
    codeField.setEditable(true)
    bottomBar.reset()
  }
}
