package it.parttimeteam.view.startup

sealed class StartUpViewEvent

// StartUp ViewEvent
case class PublicGameSubmitViewEvent(username: String, playersNumber: Int) extends StartUpViewEvent

case class PrivateGameSubmitViewEvent(username: String, privateCode: String) extends StartUpViewEvent

case class CreatePrivateGameSubmitViewEvent(username: String, playersNumber: Int) extends StartUpViewEvent

case class LeaveLobbyViewEvent(userId: String) extends StartUpViewEvent



