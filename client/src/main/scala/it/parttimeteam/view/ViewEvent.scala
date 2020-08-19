package it.parttimeteam.view

import it.parttimeteam.gamestate.PlayerGameState

sealed class ViewEvent {

}

case class PublicGameSubmitViewEvent(username: String, playersNumber: Int) extends ViewEvent

case class PrivateGameSubmitViewEvent(username: String, privateCode: String) extends ViewEvent

case class CreatePrivateGameSubmitViewEvent(username: String, playersNumber: Int) extends ViewEvent

case class LeaveLobbyViewEvent(userId: String) extends ViewEvent



case class GameStartedViewEvent(state: PlayerGameState) extends ViewEvent
