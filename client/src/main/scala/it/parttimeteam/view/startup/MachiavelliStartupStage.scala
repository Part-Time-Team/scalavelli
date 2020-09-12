package it.parttimeteam.view.startup

import it.parttimeteam.controller.startup.GameStartUpListener
import it.parttimeteam.view.startup.listeners._
import it.parttimeteam.view.startup.scenes._
import it.parttimeteam.view.utils.MachiavelliAlert
import it.parttimeteam.view.{BaseStage, ViewConfig}
import scalafx.application.Platform
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType


/**
  * Stage for startup scenes.
  */
trait MachiavelliStartupStage extends BaseStage with PrimaryStageListener {
  def notifyPrivateCode(privateCode: String): Unit

  def notifyLobbyJoined(): Unit

  def notifyError(result: String): Unit
}

/**
  * Stage for startup scenes.
  *
  * @param gameStartUpListener enables to call actions exposed by controller
  */
class MachiavelliStartupStageImpl(gameStartUpListener: GameStartUpListener) extends MachiavelliStartupStage {
  private val mainScene = new SelectScene(this, new SelectSceneListener {

    override def onSelectedPublicGame(): Unit = setCurrentScene(publicGameScene)

    override def onSelectedPrivateGame(): Unit = setCurrentScene(privateGameScene)

    override def onSelectedCreatePrivateGame(): Unit = setCurrentScene(createPrivateGame)
  })

  val stage: MachiavelliStartupStage = this
  val publicGameScene: PublicGameScene = new PublicGameScene(this, this)
  val privateGameScene: PrivateGameScene = new PrivateGameScene(this, this)
  val createPrivateGame: CreatePrivateGameStartupSceneImpl = new CreatePrivateGameStartupSceneImpl(this, this)

  var currentInnerScene: BaseStartupFormScene = _

  scene = mainScene

  onCloseRequest = _ => {
    System.exit(0)
  }

  def setCurrentScene(newScene: BaseStartupFormScene): Unit = {
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

  override def onSubmit(viewEvent: StartupViewEvent): Unit = {
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
    Platform.runLater {
      val alert: Alert = MachiavelliAlert("Error", result, AlertType.Error)
      alert.showAndWait()
    }
  }
}

/**
  * Companion object for MachiavelliStartupPrimaryStage
  */
object MachiavelliStartupStage {
  val windowWidth: Double = ViewConfig.screenWidth
  val windowHeight: Double = ViewConfig.screenHeight

  def apply(listener: GameStartUpListener): MachiavelliStartupStage = new MachiavelliStartupStageImpl(listener)

  def apply(): MachiavelliStartupStage = new MachiavelliStartupStageImpl(null)
}

trait PrimaryStageListener extends StartupSceneListener
