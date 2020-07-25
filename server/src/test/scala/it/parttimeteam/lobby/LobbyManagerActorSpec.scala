package it.parttimeteam.lobby

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import it.partitimeteam.lobby.LobbyManagerActor
import it.parttimeteam.messages.Messages.{ConnectUser, MatchFound, UserConnectionAccepted}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class LobbyManagerActorSpec extends TestKit(ActorSystem())
  with ImplicitSender
  with AnyWordSpecLike
  with BeforeAndAfterEach {

  override protected def afterEach(): Unit = TestKit.shutdownActorSystem(system)

  "a it.partitimeteam.lobby actor" should {

    "accept a user connection request" in {
      val lobbyActor = system.actorOf(LobbyManagerActor.props())
      lobbyActor ! ConnectUser("user", 2)
      expectMsg(UserConnectionAccepted)
    }


    "reply with a it.partitimeteam.match found message on players number reached" in {
      val lobbyActor = system.actorOf(LobbyManagerActor.props())
      val client1 = TestProbe("client1")
      val client2 = TestProbe("client2")
      val client3 = TestProbe("client3")
      val client4 = TestProbe("client4")

      client1.send(lobbyActor, ConnectUser("client1", 4))
      client2.send(lobbyActor, ConnectUser("client2", 4))
      client3.send(lobbyActor, ConnectUser("client3", 4))
      client4.send(lobbyActor, ConnectUser("client4", 4))

      client1.expectMsg(UserConnectionAccepted)
      client2.expectMsg(UserConnectionAccepted)
      client3.expectMsg(UserConnectionAccepted)
      client4.expectMsg(UserConnectionAccepted)

      client1.expectMsgClass(classOf[MatchFound])
      client2.expectMsgClass(classOf[MatchFound])
      client3.expectMsgClass(classOf[MatchFound])
      client4.expectMsgClass(classOf[MatchFound])

    }

  }

}
