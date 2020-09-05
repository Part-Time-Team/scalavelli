package it.parttimeteam.view.game

import it.parttimeteam.controller.game.GameListener
import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.view.game.scenes.GameScene
import it.parttimeteam.view.game.scenes.GameScene.GameSceneImpl
import it.parttimeteam.view.utils.MachiavelliAlert
import scalafx.application.Platform
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}

/**
  * MachiavelliGameStage implementation
  *
  * @param gameListener
  */
class MachiavelliGameStageImpl(gameListener: GameListener) extends MachiavelliGameStage {
  val stage: MachiavelliGameStage = this

  val gameScene: GameScene = new GameSceneImpl(stage)

  scene = gameScene

  onCloseRequest = e => {
    val alert: Alert = MachiavelliAlert("Leave the game", "Are you sure you want to leave the game? The action cannot be undone.", AlertType.Confirmation)

    alert.showAndWait match {
      case Some(b) =>
        if (b == ButtonType.OK) {
          gameListener.onViewEvent(LeaveGameEvent)
          System.exit(0)
        }

      case None =>
    }
    e.consume()
  }

  /** @inheritdoc*/
  override def showTimer(): Unit = {
    Platform.runLater({
      gameScene.showTimer()
    })
  }

  /** @inheritdoc*/
  override def setMessage(message: String): Unit = {
    Platform.runLater({
      gameScene.setMessage(message)
    })
  }

  /** @inheritdoc*/
  override def updateState(state: PlayerGameState): Unit = {
    Platform.runLater({
      gameScene.setState(state)
    })
  }

  /** @inheritdoc*/
  override def updateHistoryState(canUndo: Boolean, canRedo: Boolean): Unit = {
    gameScene.setHistoryState(canUndo, canRedo)
  }

  /** @inheritdoc*/
  override def initMatch(): Unit = {
    Platform.runLater({
      gameScene.showInitMatch()
    })
  }

  /** @inheritdoc*/
  override def matchReady(): Unit = {
    Platform.runLater({
      gameScene.hideInitMatch()
    })
  }

  /** @inheritdoc*/
  override def notifyGameEnd(gameEndType: GameEndType): Unit = {
    Platform.runLater({
      val message = gameEndType match {
        case GameWon => "Congratulations! You won the game! Do you want to play again?"

        case GameLost(winnerUsername: String) => s"This game has a winner. And the winner is.. $winnerUsername! Do you want to play again?"

        case GameEndWithError(reason: String) => s"The game ended. $reason. Do you want to play again?"
      }

      val alert = MachiavelliAlert("Game ended", message, AlertType.Information)
      alert.showAndWait match {
        case Some(b) =>
          if (b == ButtonType.OK) {
            gameListener.onViewEvent(PlayAgainEvent)
          } else {
            System.exit(0)
          }

        case None =>
      }
    })
  }

  /** @inheritdoc*/
  override def enableActions(): Unit = {
    Platform.runLater({
      gameScene.enableActions()
    })
  }

  /** @inheritdoc*/
  override def disableActions(): Unit = {
    Platform.runLater({
      gameScene.disableActions()
    })
  }

  /** @inheritdoc*/
  override def hideTimer(): Unit = {
    Platform.runLater({
      gameScene.hideTimer()
    })
  }

  /** @inheritdoc*/
  override def notifyError(result: String): Unit = {
    Platform.runLater({
      val alert: Alert = MachiavelliAlert("Error", result, AlertType.Error)
      alert.showAndWait()
    })
  }

  /** @inheritdoc*/
  override def notifyInfo(message: String): Unit = {
    Platform.runLater({
      val alert: Alert = MachiavelliAlert("", message, AlertType.Information)
      alert.showAndWait()
    })
  }


  // view actions
  /** @inheritdoc */
  override def pickCombination(combinationId: String): Unit = gameListener.onViewEvent(PickCardCombinationEvent(combinationId))

  /** @inheritdoc */
  override def endTurn(): Unit = gameListener.onViewEvent(EndTurnEvent)

  /** @inheritdoc */
  override def nextState(): Unit = gameListener.onViewEvent(RedoEvent)

  /** @inheritdoc */
  override def previousState(): Unit = gameListener.onViewEvent(UndoEvent)

  /** @inheritdoc */
  override def makeCombination(cards: Seq[Card]): Unit = gameListener.onViewEvent(MakeCombinationEvent(cards))

  /** @inheritdoc */
  override def pickCards(cards: Seq[Card]): Unit = gameListener.onViewEvent(PickCardsEvent(cards))

  /** @inheritdoc */
  override def sortHandBySuit(): Unit = gameListener.onViewEvent(SortHandBySuitEvent)

  /** @inheritdoc */
  override def sortHandByRank(): Unit = gameListener.onViewEvent(SortHandByRankEvent)

  /** @inheritdoc */
  override def updateCardCombination(combinationId: String, cards: Seq[Card]): Unit = gameListener.onViewEvent(UpdateCardCombinationEvent(combinationId, cards))

  /** @inheritdoc */
  override def leaveGame(): Unit = gameListener.onViewEvent(LeaveGameEvent)

  /** @inheritdoc */
  override def resetHistory(): Unit = gameListener.onViewEvent(ResetEvent)
}