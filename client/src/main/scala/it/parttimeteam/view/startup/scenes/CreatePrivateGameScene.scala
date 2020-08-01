package it.parttimeteam.view.startup.scenes

import it.parttimeteam.GamePreferences
import it.parttimeteam.view.startup.listeners.StartUpSceneListener
import it.parttimeteam.view.utils.{MachiavelliAlert, MachiavelliButton, MachiavelliLabel, MachiavelliTextField}
import it.parttimeteam.view.{BaseScene, CreatePrivateGameSubmitViewEvent, ViewConfig}
import javafx.scene.text.Font
import scalafx.geometry.Insets
import scalafx.geometry.Pos.{BottomRight, Center}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox, VBox}

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
class CreatePrivateGameSceneImpl(val listener: StartUpSceneListener) extends BaseScene() with CreatePrivateGameScene {
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

  usernameLabel.maxWidth <== center.width
  usernameField.maxWidth <== center.width
  selectPlayersLabel.maxWidth <== center.width
  comboBox.maxWidth <== center.width

  val bottom: HBox = new HBox()

  center.alignment = Center
  bottom.alignment = BottomRight

  val borderPane: BorderPane = new BorderPane()
  borderPane.setPadding(Insets(ViewConfig.screenPadding))
  borderPane.center = center
  borderPane.top = btnBack
  borderPane.bottom = bottom

  val codeContainer: VBox = new VBox()
  val codeLabel: Label = MachiavelliLabel("Here is your code")
  val codeValue: Label = MachiavelliLabel(ViewConfig.titleFontSize)
  codeValue.setFont(new Font(100))
  codeContainer.getChildren.addAll(codeLabel, codeValue)
  codeContainer.setVisible(false)

  override val progress: ProgressIndicator = new ProgressIndicator()
  progress.prefHeight <== bottom.height
  hideLoading()

  center.getChildren.addAll(usernameLabel, usernameField, selectPlayersLabel, comboBox, codeContainer)
  bottom.getChildren.addAll(progress, btnSubmit)

  val alert: Alert = MachiavelliAlert("Input missing", "You must enter username and select players number.", AlertType.Warning)

  def submit(): Unit = {
    val username: String = usernameField.getText
    val nPlayers: Int = comboBox.getValue

    if (!username.isEmpty && nPlayers >= GamePreferences.MIN_PLAYERS_NUM) {
      listener.onSubmit(CreatePrivateGameSubmitViewEvent(username, nPlayers))
      showLoading()
      btnSubmit.setDisable(true)
    } else {
      alert.showAndWait()
    }
  }

  override def showCode(code: String): Unit = {
    codeContainer.setVisible(true)
    codeValue.setText(code)
    hideLoading()
    System.out.println("Scene - showCode")
  }

  root = borderPane
}
