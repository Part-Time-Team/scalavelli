package it.parttimeteam.view.game

import it.parttimeteam.controller.game.GameListener
import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.view._
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.game.scenes.{GameScene, GameSceneImpl}
import it.parttimeteam.view.utils.MachiavelliAlert
import scalafx.application.JFXApp
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

/**
  * Stage for game scenes
  */
trait MachiavelliGamePrimaryStage extends JFXApp.PrimaryStage with PrimaryStageListener {
  def matchReady(): Unit

  def initMatch(): Unit

  def updateState(mockState: PlayerGameState): Unit

  def notifyError(result: String): Unit
}

/**
  * Stage for game scenes
  *
  * @param gameListener enables to call actions exposed by controller
  * @param windowWidth  the width of app window
  * @param windowHeight the height of app window  */
class MachiavelliGamePrimaryStageImpl(gameListener: GameListener, windowWidth: Double, windowHeight: Double) extends MachiavelliGamePrimaryStage {
  title = "Machiavelli"
  resizable = true
  width = windowWidth
  height = windowHeight

  val stage: MachiavelliGamePrimaryStage = this

  val gameScene: GameScene = new GameSceneImpl(stage, this)

  scene = gameScene

  onCloseRequest = _ => {
    System.exit(0)
  }

  override def notifyError(result: String): Unit = {
    val alert: Alert = MachiavelliAlert("Error", result, AlertType.Error)
    alert.showAndWait()
  }

  override def updateState(state: PlayerGameState): Unit = {
    gameScene.setState(state)
  }

  override def initMatch(): Unit = {
    gameScene.showInitMatch()
  }

  override def matchReady(): Unit = {
    gameScene.matchReady()
  }

  override def pickCombination(combinationIndex: Int): Unit = gameListener.onViewEvent(PickCardCombinationViewEvent(combinationIndex))

  override def makeCombination(cards: Seq[Card]): Unit = gameListener.onViewEvent(MakeCombinationViewEvent(cards))

  override def endTurn(): Unit = gameListener.onViewEvent(EndTurnViewEvent())

  override def nextState(): Unit = gameListener.onViewEvent(NextStateViewEvent())

  override def previousState(): Unit = gameListener.onViewEvent(PreviousStateViewEvent())
}

/**
  * Companion object for MachiavelliGamePrimaryStage
  */
object MachiavelliGamePrimaryStage {
  val windowWidth: Double = ViewConfig.screenWidth
  val windowHeight: Double = ViewConfig.screenHeight

  def apply(listener: GameListener): MachiavelliGamePrimaryStage = new MachiavelliGamePrimaryStageImpl(listener, windowWidth, windowHeight)

  def apply(): MachiavelliGamePrimaryStage = new MachiavelliGamePrimaryStageImpl(null, windowWidth, windowHeight)
}

trait PrimaryStageListener extends GameSceneListener
