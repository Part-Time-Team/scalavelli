package it.parttimeteam.lobby

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import it.partitimeteam.lobby.LobbyManagerActor
import it.parttimeteam.messages.Messages._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

class LobbyManagerActorSpec extends TestKit(ActorSystem())
  with ImplicitSender
  with AnyWordSpecLike
  with BeforeAndAfterAll {

  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)

  private val NUMBER_OF_PLAYERS = 4

  "a lobby actor" should {

    "accept a public lobby connection" in {
      val lobbyActor = system.actorOf(LobbyManagerActor.props())
      lobbyActor ! JoinPublicLobby("user", NUMBER_OF_PLAYERS)
      expectMsgType[UserAddedToLobby]
    }

    "create a new private lobby" in {
      val lobbyActor = system.actorOf(LobbyManagerActor.props())
      lobbyActor ! CreatePrivateLobby("user", NUMBER_OF_PLAYERS)
      expectMsgType[PrivateLobbyCreated]
    }

    "acccept a private lobby connection request if private lobby exists" in {
      val lobbyActor = system.actorOf(LobbyManagerActor.props())
      lobbyActor ! CreatePrivateLobby("user", NUMBER_OF_PLAYERS)
      val secondPlayer = TestProbe()
      expectMsgPF() {
        case PrivateLobbyCreated(_, lobbyCode) => {
          secondPlayer.send(lobbyActor, JoinPrivateLobby("secondPlayer", lobbyCode))
          secondPlayer.expectMsgType[UserAddedToLobby]
        }
      }
    }

    "send an error message when user tries to join to an unexisting private lobby" in {
      val lobbyActor = system.actorOf(LobbyManagerActor.props())
      lobbyActor ! JoinPrivateLobby("user", "jfhsjkdfhsjkadl")
      expectMsgType[LobbyJoinError]
    }



    //    "reply with a match found message on players number reached" in {
    //      val lobbyActor = system.actorOf(LobbyManagerActor.props())
    //      val client1 = TestProbe("client1")
    //      val client2 = TestProbe("client2")
    //      val client3 = TestProbe("client3")
    //      val client4 = TestProbe("client4")
    //
    //      client1.send(lobbyActor, ConnectUser("client1", 4))
    //      client2.send(lobbyActor, ConnectUser("client2", 4))
    //      client3.send(lobbyActor, ConnectUser("client3", 4))
    //      client4.send(lobbyActor, ConnectUser("client4", 4))
    //
    //      client1.expectMsg(UserConnectionAccepted)
    //      client2.expectMsg(UserConnectionAccepted)
    //      client3.expectMsg(UserConnectionAccepted)
    //      client4.expectMsg(UserConnectionAccepted)
    //
    //      client1.expectMsgClass(classOf[MatchFound])
    //      client2.expectMsgClass(classOf[MatchFound])
    //      client3.expectMsgClass(classOf[MatchFound])
    //      client4.expectMsgClass(classOf[MatchFound])

    //    }

  }

}
