package it.parttimeteam.view.startup

import it.parttimeteam.controller.startup.StartupListener
import it.parttimeteam.model.ErrorEvent
import it.parttimeteam.model.ErrorEvent.ServerNotFound
import it.parttimeteam.view.startup.listeners._
import it.parttimeteam.view.startup.scenes._
import it.parttimeteam.view.utils.{ScalavelliAlert, Strings}
import it.parttimeteam.view.{BaseStage, ViewConfig}
import scalafx.application.Platform
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control.{Alert, ButtonType}

/**
  * Stage for startup scenes.
  */
trait StartupStage extends BaseStage {
  /**
    * Display the private code generated
    *
    * @param privateCode the code to be displayed
    */
  def notifyPrivateCode(privateCode: String): Unit

  /**
    * Notify the user the joined lobby event
    */
  def notifyLobbyJoined(): Unit

  /**
    * Display an error alert
    *
    * @param error the error event
    */
  def notifyError(error: ErrorEvent): Unit
}

/**
  * Companion object for ScalavelliStartupPrimaryStage
  */
object StartupStage {
  val windowWidth: Double = ViewConfig.SCREEN_WIDTH
  val windowHeight: Double = ViewConfig.SCREEN_HEIGHT

  def apply(listener: StartupListener): StartupStage = new StartupStageImpl(listener)

  def apply(): StartupStage = new StartupStageImpl(null)

  /** @inheritdoc */
  class StartupStageImpl(gameStartupListener: StartupListener) extends StartupStage {
    private val mainScene: SelectScene = new SelectScene(this, new SelectSceneListener {

      override def onSelectedPublicGame(): Unit = setCurrentScene(publicGameScene)

      override def onSelectedPrivateGame(): Unit = setCurrentScene(privateGameScene)

      override def onSelectedCreatePrivateGame(): Unit = setCurrentScene(createPrivateGame)
    })

    val stage: StartupStage = this

    val listener: StartupSceneListener = new StartupSceneListener {

      override def onBackPressed(): Unit = {
        gameStartupListener.onViewEvent(LeaveLobbyViewEvent)

        currentInnerScene.resetScreen()
        scene = mainScene
      }

      override def onSubmit(viewEvent: StartupViewEvent): Unit = {
        gameStartupListener.onViewEvent(viewEvent)
      }
    }

    scene = mainScene

    val publicGameScene: PublicGameScene = new PublicGameScene(this, listener)
    val privateGameScene: PrivateGameScene = new PrivateGameScene(this, listener)
    val createPrivateGame: CreatePrivateGameStartupSceneImpl = new CreatePrivateGameStartupSceneImpl(this, listener)

    var currentInnerScene: StartupFormScene = _

    onCloseRequest = _ => {
      System.exit(0)
    }

    // Controller actions
    override def notifyPrivateCode(privateCode: String): Unit = {
      Platform.runLater(createPrivateGame.showCode(privateCode))
      notifyLobbyJoined()
    }

    override def notifyLobbyJoined(): Unit = {
      Platform.runLater(currentInnerScene.showMessage(Strings.WAITING_FOR_PLAYERS))
    }

    override def notifyError(error: ErrorEvent): Unit = {
      Platform.runLater {
        var alert: Alert = ScalavelliAlert(Strings.ERROR_DIALOG_TITLE, error, AlertType.Error, stage)

        if (error == ServerNotFound) {
          val buttonTypeRetry = new ButtonType(Strings.RETRY, ButtonData.Yes)
          val buttonTypeCancel = new ButtonType(Strings.CLOSE, ButtonData.CancelClose)

          alert.getButtonTypes.setAll(buttonTypeRetry, buttonTypeCancel)
          alert.showAndWait match {
            case Some(b) => {
              if (b == buttonTypeRetry) {
                gameStartupListener.onViewEvent(RetryServerConnection)
              }
            }

            case None =>
          }
        } else {
          alert = ScalavelliAlert(Strings.ERROR_DIALOG_TITLE, error, AlertType.Error, stage)
          alert.showAndWait()
        }
      }
    }

    private def setCurrentScene(newScene: StartupFormScene): Unit = {
      scene = newScene
      currentInnerScene = newScene
    }

  }

}
