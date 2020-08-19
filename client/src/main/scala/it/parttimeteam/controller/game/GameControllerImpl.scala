package it.parttimeteam.controller.game
import akka.actor.ActorRef
import it.parttimeteam.Board
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{CardCombination, Hand}
import it.parttimeteam.core.player.Player
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.view.game.MachiavelliGamePrimaryStage
import scalafx.application.JFXApp

class GameControllerImpl extends GameController {

  override def start(app: JFXApp, gameRef: ActorRef): Unit = {
    val gameStage: MachiavelliGamePrimaryStage = MachiavelliGamePrimaryStage(this)

    app.stage = gameStage

    // Mock state
    var board: Board = Board()
    board = board.addCombination(CardCombination(List(new Card("A", "♠"), new Card("2", "♠"))))
    board = board.addCombination(CardCombination(List(new Card("3", "♠"), new Card("4", "♠"))))
    board = board.addCombination(CardCombination(List(new Card("5", "♠"), new Card("6", "♠"), new Card("7", "♠"))))

    var hand: Hand = new Hand()
    hand = hand.addPlayerCards(new Card("A", "♥"),
      new Card("2", "♥"),
      new Card("3", "♥"),
      new Card("4", "♥"),
      new Card("5", "♥"),
      new Card("6", "♥"),
      new Card("7", "♥"))

    hand = hand.addTableCards(new Card("K", "♥"))

    val pippo: Seq[Player] = List(
      new Player("Matteo", "aaaa", hand),
      new Player("Daniele", "bbbb", hand),
      new Player("Lorenzo", "cccc", hand),
    )

    val mockState: PlayerGameState = new PlayerGameState(board, hand, pippo)
    gameStage.updateState(mockState)
  }
}
