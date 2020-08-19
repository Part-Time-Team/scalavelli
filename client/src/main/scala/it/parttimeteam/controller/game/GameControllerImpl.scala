package it.parttimeteam.controller.game
import akka.actor.ActorRef
import it.parttimeteam.Board
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{CardCombination, Hand}
import it.parttimeteam.gamestate.{Opponent, PlayerGameState}
import it.parttimeteam.view._
import it.parttimeteam.view.game.MachiavelliGamePrimaryStage
import scalafx.application.JFXApp

class GameControllerImpl extends GameController {
  var gameStage: MachiavelliGamePrimaryStage = _

  override def start(app: JFXApp, gameRef: ActorRef): Unit = {
    gameStage = MachiavelliGamePrimaryStage(this)

    app.stage = gameStage

    gameStage.initMatch()

    // TODO: Luca - Remove when server works
    gameStage.matchReady()
    gameStage.updateState(getMockState)
  }

  override def onViewEvent(viewEvent: ViewEvent): Unit = viewEvent match {
    case GameStartedViewEvent(initialState: PlayerGameState) => {
      gameStage.matchReady()
      gameStage.updateState(initialState)
    }
    case _ =>
  }

  private def getMockState: PlayerGameState = {
    var board: Board = Board()
    board = board.addCombination(CardCombination(List(Card("A", "♠"), Card("2", "♠"))))
    board = board.addCombination(CardCombination(List(Card("3", "♠"), Card("4", "♠"))))
    board = board.addCombination(CardCombination(List(Card("5", "♠"), Card("6", "♠"), Card("7", "♠"))))

    var hand: Hand = Hand()
    hand = hand.addPlayerCards(
      Card("A", "♥"),
      Card("2", "♥"),
      Card("3", "♥"),
      Card("4", "♥"),
      Card("5", "♥"),
      Card("6", "♥"),
      Card("7", "♥"))

    hand = hand.addTableCards(
      Card("K", "♥"),
      Card("Q", "♠"))

    val pippo: Seq[Opponent] = List(
      Opponent("Matteo", 5),
      Opponent("Daniele", 7),
      Opponent("Lorenzo", 3),
    )

    PlayerGameState(board, hand, pippo)
  }
}
