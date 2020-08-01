package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.startup.listeners.StartUpSceneListener
import it.parttimeteam.view.utils.{MachiavelliAlert, MachiavelliButton, MachiavelliLabel, MachiavelliTextField}
import it.parttimeteam.view.{BaseScene, PrivateGameSubmitViewEvent, ViewConfig}
import scalafx.geometry.Insets
import scalafx.geometry.Pos.{BottomRight, Center}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox, VBox}

/**
  * Allow to participate to a private game by inserting a private code.
  *
  * @param listener to interact with parent stage
  */
class PrivateGameScene(val listener: StartUpSceneListener) extends BaseScene() {
  val btnBack: Button = MachiavelliButton("<", listener.onBackPressed)
  val btnSubmit: Button = MachiavelliButton("Send", submit)

  val usernameLabel: Label = MachiavelliLabel("Username", ViewConfig.formLabelFontSize)
  val usernameField: TextField = MachiavelliTextField("Username")

  val codeLabel: Label = MachiavelliLabel("Code", ViewConfig.formLabelFontSize)
  val codeField: TextField = MachiavelliTextField("Code")


  val borderPane: BorderPane = new BorderPane()
  borderPane.setPadding(Insets(ViewConfig.screenPadding))

  val center: VBox = new VBox()
  center.spacing = ViewConfig.formSpacing
  center.maxWidth = ViewConfig.formWidth

  usernameLabel.maxWidth <== center.width
  usernameField.maxWidth <== center.width
  codeLabel.maxWidth <== center.width
  codeField.maxWidth <== center.width

  val bottom: HBox = new HBox()

  center.alignment = Center
  bottom.alignment = BottomRight

  borderPane.center = center
  borderPane.top = btnBack
  borderPane.bottom = bottom

  override val progress: ProgressIndicator = new ProgressIndicator()
  progress.prefHeight <== bottom.height
  hideLoading()

  center.getChildren.addAll(usernameLabel, usernameField, codeLabel, codeField)
  bottom.getChildren.addAll(progress, btnSubmit)

  val alert: Alert = MachiavelliAlert("Input missing", "You must enter username and code.", AlertType.Warning)

  root = borderPane

  def submit(): Unit = {
    val username: String = usernameField.getText
    val code: String = codeField.getText

    if (!username.isEmpty && !code.isEmpty) {
      listener.onSubmit(PrivateGameSubmitViewEvent(username, code))
      showLoading()
      btnSubmit.setDisable(true)
    } else {
      alert.showAndWait()
    }
  }
}
