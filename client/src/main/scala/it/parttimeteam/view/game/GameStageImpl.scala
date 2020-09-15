package it.parttimeteam.view.game

import it.parttimeteam.controller.ViewMessage
import it.parttimeteam.controller.game.GameListener
import it.parttimeteam.core.cards.Card
import it.parttimeteam.model.ErrorEvent
import it.parttimeteam.model.game.ClientGameState
import it.parttimeteam.view.game.listeners.GameStageListener
import it.parttimeteam.view.game.scenes.{GameScene, GameSceneImpl}
import it.parttimeteam.view.utils.{ScalavelliAlert, StringParser, Strings}
import scalafx.application.Platform
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}

/**
  * GameStage implementation
  *
  * @param gameListener listener for view actions
  */
class GameStageImpl(gameListener: GameListener) extends GameStage {
  val stage: GameStage = this

  val listener: GameStageListener = new GameStageListener() {
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
    override def leaveGame(): Unit = {
      val alert: Alert = ScalavelliAlert(Strings.LEAVE_GAME_DIALOG_TITLE, Strings.LEAVE_GAME_DIALOG_MESSAGE, AlertType.Confirmation, stage)

      alert.showAndWait match {
        case Some(b) =>
          if (b == ButtonType.OK) {
            gameListener.onViewEvent(LeaveGameEvent)
            System.exit(0)
          }

        case None =>
      }
    }

    /** @inheritdoc */
    override def resetHistory(): Unit = gameListener.onViewEvent(ResetEvent)

  }

  val gameScene: GameScene = new GameSceneImpl(stage, listener)

  scene = gameScene

  onCloseRequest = _ => System.exit(0)

  /** @inheritdoc*/
  override def setMessage(message: ViewMessage): Unit = {
    Platform.runLater({
      gameScene.setMessage(StringParser.parseMessage(message))
    })
  }

  /** @inheritdoc*/
  override def updateState(viewGameState: ClientGameState): Unit = {
    Platform.runLater({
      gameScene.setState(viewGameState)
    })
  }

  /** @inheritdoc*/
  override def initMatch(): Unit = {
    Platform.runLater({
      gameScene.disableActions()
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
      val message: String = gameEndType match {
        case GameWon => Strings.GAME_WON_ALERT_MESSAGE

        case GameLost(winnerUsername: String) => Strings.GAME_LOST_ALERT_MESSAGE(winnerUsername)

        case GameEndPlayerLeft => Strings.GAME_END_PLAYER_LEFT_MESSAGE

        case _ => ""
      }

      val alert = ScalavelliAlert(Strings.GAME_ENDED_ALERT_TITLE, message, AlertType.Information, this)
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
  override def notifyError(error: ErrorEvent): Unit = {
    Platform.runLater({
      val alert: Alert = ScalavelliAlert(Strings.ERROR_DIALOG_TITLE, error, AlertType.Error, this)
      alert.showAndWait()
    })
  }

  /** @inheritdoc*/
  override def notifyInfo(message: String): Unit = {
    Platform.runLater({
      val alert: Alert = ScalavelliAlert("", message, AlertType.Information, this)
      alert.showAndWait()
    })
  }

  /** @inheritdoc*/
  override def setInTurn(): Unit = {
    gameScene.setInTurn(true)
    Platform.runLater({
      val alert = ScalavelliAlert("", Strings.YOUR_TURN_ALERT_MESSAGE, AlertType.Information, this)
      alert.showAndWait match {
        case Some(b) =>
          if (b == ButtonType.OK) {
            gameListener.onViewEvent(TurnStartedEvent)
          } else {
            System.exit(0)
          }

        case None =>
      }

      setMessage(ViewMessage.YourTurn)
    })
  }

  /** @inheritdoc*/
  override def setTurnEnded(): Unit = {
    gameScene.setInTurn(false)
    Platform.runLater({
      gameScene.setMessage("")
      gameScene.hideTimer()
    })
  }

  /** @inheritdoc*/
  override def showTimer(minutes: Long, seconds: Long): Unit = {
    Platform.runLater({
      gameScene.showTimer(minutes, seconds)
    })
  }

  /** @inheritdoc*/
  override def updateTimer(minutes: Long, seconds: Long): Unit = {
    Platform.runLater({
      gameScene.updateTimer(minutes, seconds)
    })
  }

  /** @inheritdoc*/
  override def notifyTimerEnded(): Unit = {
    Platform.runLater({
      gameScene.notifyTimerEnd()
      notifyInfo(Strings.TIMER_END_INFO_MESSAGE)
    })
  }

}