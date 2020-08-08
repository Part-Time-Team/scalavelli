package it.parttimeteam

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit, TestProbe}
import it.partitimeteam.lobby.LobbyManagerActor
import it.parttimeteam.entities.GamePlayerState
import it.parttimeteam.messages.GameMessage.{GameStarted, Ready}
import it.parttimeteam.messages.LobbyMessages.{JoinPublicLobby, UserAddedToLobby}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

class LobbyAndMatchStartFlowSpec extends TestKit(ActorSystem())
  with ImplicitSender
  with AnyWordSpecLike
  with BeforeAndAfterAll {

  private val NUMBER_OF_PLAYERS = 2

  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)

  "A correct flow" should {

    "add users to lobby and if number of player is enough, start a match and notify the players" in {
      val lobbyActor = TestActorRef[LobbyManagerActor](LobbyManagerActor.props())
      val probe = TestProbe()
      probe.send(lobbyActor, JoinPublicLobby("otherPlayer", NUMBER_OF_PLAYERS))
      lobbyActor ! JoinPublicLobby("me", NUMBER_OF_PLAYERS)

      probe.expectMsgType[UserAddedToLobby]
      expectMsgType[UserAddedToLobby]

      probe.expectMsgClass(GameStarted.getClass)
      expectMsgClass(GameStarted.getClass)

    }

  }


}