package it.parttimeteam.`match`

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import it.parttimeteam.`match`.GameMatchActor
import it.parttimeteam.core
import it.parttimeteam.core.{GameManager, GameState}
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{Board, CardCombination, Deck, Hand}
import it.parttimeteam.core.player.Player
import it.parttimeteam.core.player.Player.PlayerId
import it.parttimeteam.entities.GamePlayer
import it.parttimeteam.messages.GameMessage.{CardDrawn, GamePlayers, GameStateUpdated, PlayerActionMade, PlayerTurn, Ready}
import it.parttimeteam.messages.LobbyMessages.MatchFound
import it.parttimeteam.DrawCard
import org.scalamock.scalatest.MockFactory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

class GameStateMatchActorSpec extends TestKit(ActorSystem("test", ConfigFactory.load("test")))
  with ImplicitSender
  with AnyWordSpecLike
  with BeforeAndAfterAll
  with MockFactory {

  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)

  private val NUMBER_OF_PLAYERS = 2

  "a game actor" should {

    "accept players and notify game started with initial state" in {
      val gameActor = TestActorRef[GameMatchActor](GameMatchActor.props(NUMBER_OF_PLAYERS, new FakeGameManager()))
      val player1 = TestProbe()
      val player2 = TestProbe()

      gameActor ! GamePlayers(Seq(GamePlayer("id1", "player1", player1.ref), GamePlayer("id2", "player2", player2.ref)))
      player1.expectMsgType[MatchFound]
      player1.send(gameActor, Ready("id1", player1.ref))
      player2.expectMsgType[MatchFound]
      player2.send(gameActor, Ready("id2", player2.ref))

      player1.expectMsgType[GameStateUpdated]
      player2.expectMsgType[GameStateUpdated]

      player1.expectMsg(PlayerTurn)
      player1.send(gameActor, PlayerActionMade("id1", DrawCard))
      player1.expectMsgType[GameStateUpdated]
      player2.expectMsgType[GameStateUpdated]
      player1.expectMsg(CardDrawn(FakeGameManager.cardToDraw))
      player2.expectMsg(PlayerTurn)
    }

  }

  object FakeGameManager {
    val cardToDraw = Card.string2card("2♣R")
  }

  class FakeGameManager extends GameManager {
    /**
     * Create a new game state from players ids.
     *
     * @param players List of players ids.
     * @return New Game State.
     */
    override def create(players: Seq[PlayerId]): GameState = core.GameState(
      Deck(List()),
      Board.empty,
      players.map(id => Player(id, id, Hand(List(), List()))))

    override def draw(deck: Deck): (Deck, Card) = (deck, FakeGameManager.cardToDraw)

    override def validateTurn(board: Board, hand: Hand): Boolean = ???

    override def validateCombination(combination: CardCombination): Boolean = ???

    override def pickBoardCards(hand: Hand, board: Board, cards: Card*): Either[String, (Hand, Board)] = ???

    override def playCombination(hand: Hand, board: Board, combination: CardCombination): (Hand, Board) = ???
  }

}