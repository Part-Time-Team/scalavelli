package it.parttimeteam.`match`

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import it.parttimeteam.`match`.GameMatchActor
import it.parttimeteam.core.GameManager
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{CardCombination, Deck, Hand}
import it.parttimeteam.core.player.Player
import it.parttimeteam.core.player.Player.PlayerId
import it.parttimeteam.entities.GamePlayer
import it.parttimeteam.messages.GameMessage.{GamePlayers, GameStateUpdated, Ready}
import it.parttimeteam.messages.LobbyMessages.MatchFound
import it.parttimeteam.{Board, GameState}
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

    }

  }


  class FakeGameManager extends GameManager {
    /**
     * Create a new game state from players ids.
     *
     * @param players List of players ids.
     * @return New Game State.
     */
    override def create(players: Seq[PlayerId]): GameState = GameState(
      Deck(List()),
      Board.empty,
      players.map(id => Player(id, id, Hand(List(), List()))))

    /**
     * Draw a card from the top of the deck.
     *
     * @param deck Deck to draw.
     * @return Deck tail and card picked.
     */
    override def draw(deck: Deck): (Deck, Card) = ???

    /**
     * Validate an entire board and hand.
     *
     * @param board Board to validate.
     * @param hand  Hand to validate.
     * @return True if is validated, false anywhere.
     */
    override def validateTurn(board: Board, hand: Hand): Boolean = ???

    /**
     * Validate a card combination.
     *
     * @param combination Combination to validate.
     * @return True if is validated, false anywhere.
     */
    override def validateCombination(combination: CardCombination): Boolean = ???

    /**
     * Pick cards from a combination on the table.
     *
     * @param hand  Hand where put cards picked.
     * @param board Board where pick cards.
     * @param cards Cards to pick.
     * @return Hand and Board updated.
     */
    override def pickTableCards(hand: Hand, board: Board, cards: Card*): (Hand, Board) = ???

    /**
     * Play cards from hand to board.
     *
     * @param hand        Hand where to pick cards.
     * @param board       Board where put cards.
     * @param combination Combination to pick.
     * @return Hand and Board updated.
     */
    override def playCombination(hand: Hand, board: Board, combination: CardCombination): (Hand, Board) = ???
  }

}