package it.parttimeteam.`match`

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import it.parttimeteam.`match`.GameMatchActor.GamePlayers
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{Board, Deck, Hand}
import it.parttimeteam.core.player.Player
import it.parttimeteam.core.player.Player.{PlayerId, PlayerName}
import it.parttimeteam.core.{GameError, GameInterface, GameState}
import it.parttimeteam.gamestate.{Opponent, PlayerGameState}
import it.parttimeteam.messages.GameMessage._
import it.parttimeteam.messages.LobbyMessages.MatchFound
import it.parttimeteam.{DrawCard, common, core}
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
      val gameActor = TestActorRef[GameMatchActor](GameMatchActor.props(NUMBER_OF_PLAYERS, new GameMatchManager(new FakeGameInterface())
      ))
      val player1 = TestProbe()
      val player2 = TestProbe()

      gameActor ! GamePlayers(Seq(common.GamePlayer("id1", "player1", player1.ref), common.GamePlayer("id2", "player2", player2.ref)))
      player1.expectMsgType[MatchFound]
      player1.send(gameActor, Ready("id1", player1.ref))
      player2.expectMsgType[MatchFound]
      player2.send(gameActor, Ready("id2", player2.ref))

      player1.expectMsgType[GameStateUpdated]
      player2.expectMsgType[GameStateUpdated]

      player1.expectMsg(PlayerTurn)
      player1.send(gameActor, PlayerActionMade("id1", DrawCard))
      player1.expectMsgType[GameStateUpdated]
      player2.expectMsgType[OpponentInTurn]
      player1.expectMsg(TurnEnded)
      player2.expectMsg(GameStateUpdated(PlayerGameState(Board(List()), Hand(List(), List()), List(Opponent("player1", 1))))
      )
    }

  }

  object FakeGameInterface {
    val cardToDraw = Card.string2card("2CR")
  }

  class FakeGameInterface extends GameInterface {
    /**
     * Create a new game state from players ids.
     *
     * @param players List of players ids.
     * @return New Game State.
     */
    override def create(players: Seq[(PlayerId, PlayerName)]): GameState = core.GameState(
      Deck(List()),
      Board.empty,
      players.map(pair => Player(pair._2, pair._1, Hand(List(), List()))))

    override def draw(deck: Deck): (Deck, Card) = (deck, FakeGameInterface.cardToDraw)

    override def validateMove(board: Board, hand: Hand): Boolean = ???

    override def validateCombination(cards: Seq[Card]): Boolean = ???

    override def pickBoardCards(hand: Hand, board: Board, cards: Seq[Card]): Either[GameError, (Hand, Board)] = ???

    override def playCombination(hand: Hand, board: Board, cards: Seq[Card]): Either[GameError, (Hand, Board)] = ???

    override def putCardsInCombination(hand: Hand, board: Board, id: String, cards: Seq[Card]): Either[GameError, (Hand, Board)] = ???

    override def validateTurn(board: Board, startBoard: Board, hand: Hand, startHand: Hand): Boolean = ???
  }

}