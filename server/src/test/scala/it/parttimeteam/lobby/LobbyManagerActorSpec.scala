package it.parttimeteam.lobby

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import it.parttimeteam.lobby.LobbyManagerActor
import it.parttimeteam.messages.LobbyMessages._
import it.parttimeteam.messages.PrivateLobbyIdNotValidError
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

class LobbyManagerActorSpec extends TestKit(ActorSystem("test", ConfigFactory.load("test")))
  with ImplicitSender
  with AnyWordSpecLike
  with BeforeAndAfterAll {

  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)

  private val NUMBER_OF_PLAYERS = 4

  "a lobby actor" should {

    "successfully connect to the server" in {
      val lobbyActor = system.actorOf(LobbyManagerActor.props())
      val client = TestProbe()
      lobbyActor ! Connect(client.ref)
      client.expectMsgType[Connected]
    }


    "accept a public lobby connection" in {
      val lobbyActor = system.actorOf(LobbyManagerActor.props())
      val client = TestProbe()
      lobbyActor ! Connect(client.ref)
      client.expectMsgPF() {
        case Connected(id) => {
          lobbyActor ! JoinPublicLobby(id, "user", NUMBER_OF_PLAYERS)
          client.expectMsgType[UserAddedToLobby]
        }
      }


    }

    "create a new private lobby" in {
      val lobbyActor = system.actorOf(LobbyManagerActor.props())
      val client = TestProbe()
      lobbyActor ! Connect(client.ref)
      client.expectMsgPF() {
        case Connected(id) => {
          lobbyActor ! CreatePrivateLobby(id, "user", NUMBER_OF_PLAYERS)
          client.expectMsgType[PrivateLobbyCreated]
        }
      }
    }

    "acccept a private lobby connection request if private lobby exists" in {
      val lobbyActor = system.actorOf(LobbyManagerActor.props())
      val client = TestProbe()
      lobbyActor ! Connect(client.ref)
      client.expectMsgPF() {
        case Connected(id) => {
          lobbyActor ! CreatePrivateLobby(id, "user", NUMBER_OF_PLAYERS)
          val secondPlayer = TestProbe()
          client.expectMsgPF() {
            case PrivateLobbyCreated(lobbyCode) => {
              lobbyActor ! Connect(secondPlayer.ref)
              secondPlayer.expectMsgPF() {
                case Connected(secondId) => {
                  secondPlayer.send(lobbyActor, JoinPrivateLobby(secondId, "secondPlayer", lobbyCode))
                  secondPlayer.expectMsgType[UserAddedToLobby]
                }
              }
            }
          }
        }
      }
    }

  }

  "send an error message when user tries to join to an unexisting private lobby" in {
    val lobbyActor = system.actorOf(LobbyManagerActor.props())
    val client = TestProbe()
    lobbyActor ! Connect(client.ref)
    client.expectMsgPF() {
      case Connected(id) => {
        lobbyActor ! JoinPrivateLobby(id, "user", "jfhsjkdfhsjkadl")
        client.expectMsg(LobbyError(PrivateLobbyIdNotValidError))
      }
    }

  }

}


