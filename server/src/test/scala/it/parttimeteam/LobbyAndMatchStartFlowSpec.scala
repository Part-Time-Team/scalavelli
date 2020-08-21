package it.parttimeteam

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import it.partitimeteam.lobby.LobbyManagerActor
import it.parttimeteam.messages.LobbyMessages._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

class LobbyAndMatchStartFlowSpec extends TestKit(ActorSystem("test", ConfigFactory.load("test")))
  with ImplicitSender
  with AnyWordSpecLike
  with BeforeAndAfterAll {

  private val NUMBER_OF_PLAYERS = 2

  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)

  "A correct flow" should {

    "add users to lobby and if number of player is enough, start a match and notify the players" in {
      val lobbyActor = TestActorRef[LobbyManagerActor](LobbyManagerActor.props())
      val client1 = TestProbe()
      val client2 = TestProbe()

      lobbyActor ! Connect(client1.ref)

      client1.expectMsgPF() {
        case Connected(client1Id) => {
          lobbyActor ! Connect(client2.ref)

          client2.expectMsgPF() {
            case Connected(client2Id) => {
              lobbyActor ! JoinPublicLobby(client1Id, "me", NUMBER_OF_PLAYERS)
              lobbyActor ! JoinPublicLobby(client2Id, "me2", NUMBER_OF_PLAYERS)

              client1.expectMsgType[UserAddedToLobby]
              client2.expectMsgType[UserAddedToLobby]

              client1.expectMsgType[MatchFound]
              client2.expectMsgType[MatchFound]

            }
          }

        }
      }


    }

  }


}
