package it.parttimeteam.`match`

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import it.partitimeteam.`match`.GameMatchActor
import it.parttimeteam.entities.GamePlayer
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage.{GamePlayers, GameStarted, Ready}
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
      val gameActor = TestActorRef[GameMatchActor](GameMatchActor.props(NUMBER_OF_PLAYERS))
      val player1 = TestProbe()
      val player2 = TestProbe()

      gameActor ! GamePlayers(Seq(GamePlayer("id1", "player1", player1.ref), GamePlayer("id2", "player2", player2.ref)))
      player1.expectMsg(GameStarted)
      player1.send(gameActor, Ready("id1"))
      player2.expectMsg(GameStarted)
      player2.send(gameActor, Ready("id2"))

      player1.expectMsgType[PlayerGameState]
      player2.expectMsgType[PlayerGameState]

    }

  }

}
