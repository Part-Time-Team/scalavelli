package it.parttimeteam.view.startup

import it.parttimeteam.controller.startup.GameStartUpListener
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.startup.listeners._
import it.parttimeteam.view.startup.scenes._
import it.parttimeteam.view.utils.MachiavelliAlert
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType



/**
  * Stage for startup scenes.
  */
trait MachiavelliStartUpPrimaryStage extends JFXApp.PrimaryStage with PrimaryStageListener {
  def notifyPrivateCode(privateCode: String): Unit

  def notifyLobbyJoined(): Unit

  def notifyError(result: String): Unit
}

/**
  * Stage for startup scenes.
  *
  * @param gameStartUpListener enables to call actions exposed by controller
  * @param windowWidth         the width of app window
  * @param windowHeight        the height of app window
  */
class MachiavelliStartUpPrimaryStageImpl(gameStartUpListener: GameStartUpListener, windowWidth: Double, windowHeight: Double) extends MachiavelliStartUpPrimaryStage {
  title = "Machiavelli"
  resizable = true
  width = windowWidth
  height = windowHeight

  private val mainScene = new SelectScene(this, new SelectSceneListener {

    override def onSelectedPublicGame(): Unit = setCurrentScene(publicGameScene)

    override def onSelectedPrivateGame(): Unit = setCurrentScene(privateGameScene)

    override def onSelectedCreatePrivateGame(): Unit = setCurrentScene(createPrivateGame)
  })

  val stage: MachiavelliStartUpPrimaryStage = this
  val publicGameScene: PublicGameStartUpScene = new PublicGameStartUpScene(this, this)
  val privateGameScene: PrivateGameStartUpScene = new PrivateGameStartUpScene(this, this)
  val createPrivateGame: CreatePrivateGameStartUpSceneImpl = new CreatePrivateGameStartUpSceneImpl(this, this)

  var currentInnerScene: BaseStartUpFormScene = _

  scene = mainScene

  onCloseRequest = _ => {
    System.exit(0)
  }

  def setCurrentScene(newScene: BaseStartUpFormScene): Unit = {
    scene = newScene
    currentInnerScene = newScene
  }

  // View actions
  override def onBackPressed(): Unit = {
    // TODO: Luca - Call leave lobby only when joined
    gameStartUpListener.onViewEvent(LeaveLobbyViewEvent)

    currentInnerScene.resetScreen()
    scene = mainScene
  }

  override def onSubmit(viewEvent: StartUpViewEvent): Unit = {
    gameStartUpListener.onViewEvent(viewEvent)
  }

  // Controller actions
  override def notifyPrivateCode(privateCode: String): Unit = {
    Platform.runLater(createPrivateGame.showCode(privateCode))
    notifyLobbyJoined()
  }

  override def notifyLobbyJoined(): Unit = {
    Platform.runLater(currentInnerScene.showMessage("Waiting for other players"))
  }

  override def notifyError(result: String): Unit = {
    val alert: Alert = MachiavelliAlert("Error", result, AlertType.Error)
    Platform.runLater(alert.showAndWait())
  }
}

/**
  * Companion object for MachiavelliStartupPrimaryStage
  */
object MachiavelliStartUpPrimaryStage {
  val windowWidth: Double = ViewConfig.screenWidth
  val windowHeight: Double = ViewConfig.screenHeight

  def apply(listener: GameStartUpListener): MachiavelliStartUpPrimaryStage = new MachiavelliStartUpPrimaryStageImpl(listener, windowWidth, windowHeight)

  def apply(): MachiavelliStartUpPrimaryStage = new MachiavelliStartUpPrimaryStageImpl(null, windowWidth, windowHeight)
}

trait PrimaryStageListener extends StartUpSceneListener
