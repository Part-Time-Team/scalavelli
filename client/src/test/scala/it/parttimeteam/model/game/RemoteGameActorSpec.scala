package it.parttimeteam.model.game

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import com.typesafe.config.ConfigFactory
import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage._
import org.scalamock.scalatest.MockFactory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

class RemoteGameActorSpec extends TestKit(ActorSystem("test", ConfigFactory.load("test")))
  with ImplicitSender
  with AnyWordSpecLike
  with BeforeAndAfterAll
  with MockFactory {

  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)

  private def provideListener = stub[MatchServerResponseListener]

  private def provideActorRef(listener: MatchServerResponseListener) = TestActorRef[RemoteGameActor](RemoteGameActor.props(listener))

  "a remote game actor" should {

    val mockListener = provideListener
    val actor = provideActorRef(mockListener)

    "notify the listener on game state updated" in {
      actor ! GameStateUpdated(PlayerGameState(null, null, null))
      (mockListener.gameStateUpdated _).verify(*).once()
    }

    "notify the lister on turn started" in {
      actor ! PlayerTurn
      (mockListener.turnStarted _).verify().once()
    }

    "notify the lister on opponent turn started" in {
      val name = "sampleName"
      actor ! OpponentInTurn(name)
      (mockListener.opponentInTurn _).verify(name).once()
    }

    "notify the lister on turn ended" in {
      actor ! TurnEnded
      (mockListener.turnEnded _).verify().once()
    }

    "notify the lister on turn ended with card drawn" in {
      val sampleCard = Card.string2card("2♣R")
      actor ! CardDrawn(sampleCard)
      (mockListener.turnEndedWithCartDrawn _).verify(sampleCard).once()
    }

    "notify the lister on game finished with a win" in {
      actor ! Won
      (mockListener.gameWon _).verify().once()
    }

    "notify the lister on game finished with a lost " in {
      val winnerName = "winner"
      actor ! Lost(winnerName)
      (mockListener.gameLost _).verify(winnerName).once()
    }

  }

}
