package it.parttimeteam.view.game

import it.parttimeteam.Constants
import it.parttimeteam.controller.game.GameListener
import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.view._
import it.parttimeteam.view.game.listeners.GameStageToControllerListener
import it.parttimeteam.view.game.scenes.{GameScene, GameSceneImpl}
import it.parttimeteam.view.utils.MachiavelliAlert
import scalafx.application.JFXApp
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

/**
  * Stage for game scenes
  */
trait MachiavelliGamePrimaryStage extends JFXApp.PrimaryStage with PrimaryStageListener {
  def showTimer(): Unit

  def notifyInfo(message: String): Unit

  def setMessage(message: String): Unit

  def matchReady(): Unit

  def initMatch(): Unit

  def updateState(mockState: PlayerGameState): Unit

  def notifyError(result: String): Unit
}

/**
  * Stage for game scenes
  *
  * @param gameControllerListener enables to call actions exposed by controller
  * @param windowWidth            the width of app window
  * @param windowHeight           the height of app window  */
class MachiavelliGamePrimaryStageImpl(gameControllerListener: GameListener, windowWidth: Double, windowHeight: Double) extends MachiavelliGamePrimaryStage {
  title = Constants.Client.GAME_NAME
  resizable = true
  width = windowWidth
  height = windowHeight

  val stage: MachiavelliGamePrimaryStage = this

  val gameScene: GameScene = new GameSceneImpl(stage)

  scene = gameScene

  onCloseRequest = _ => {
    System.exit(0)
  }

  override def notifyError(result: String): Unit = {
    val alert: Alert = MachiavelliAlert("Error", result, AlertType.Error)
    alert.showAndWait()
  }

  override def notifyInfo(message: String): Unit = {
    val alert: Alert = MachiavelliAlert("", message, AlertType.Information)
    alert.showAndWait()
  }

  // controller actions
  override def showTimer(): Unit = gameScene.showTimer()

  override def setMessage(message: String): Unit = gameScene.setMessage(message)

  override def updateState(state: PlayerGameState): Unit = gameScene.setState(state)

  override def initMatch(): Unit = gameScene.showInitMatch()

  override def matchReady(): Unit = gameScene.hideInitMatch()

  // view actions
  override def pickCombination(combinationId: String): Unit = gameControllerListener.onViewEvent(PickCardCombinationViewEvent(combinationId))

  override def endTurn(): Unit = gameControllerListener.onViewEvent(EndTurnViewEvent)

  override def nextState(): Unit = gameControllerListener.onViewEvent(RedoViewEvent)

  override def previousState(): Unit = gameControllerListener.onViewEvent(UndoViewEvent)

  override def makeCombination(cards: Seq[Card]): Unit = gameControllerListener.onViewEvent(MakeCombinationViewEvent(cards))

  override def pickCards(cards: Seq[Card]): Unit = gameControllerListener.onViewEvent(PickCardsViewEvent(cards))

  override def sortHandBySuit(): Unit = gameControllerListener.onViewEvent(SortHandBySuitViewEvent)

  override def sortHandByRank(): Unit = gameControllerListener.onViewEvent(SortHandByRankViewEvent)
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

trait PrimaryStageListener extends GameStageToControllerListener
