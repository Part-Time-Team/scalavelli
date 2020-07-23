import Messages.{ConnectUser, UserConnectionAccepted}
import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.wordspec.AnyWordSpecLike

class LobbyActorSpec extends TestKit(ActorSystem())
  with ImplicitSender
  with AnyWordSpecLike {

  "a lobby actor" should {

    "accept a user connection request" in {
      val lobbyActor = system.actorOf(LobbyActor.props())
      lobbyActor ! ConnectUser("user")
      expectMsg(UserConnectionAccepted)
    }


  }

}
