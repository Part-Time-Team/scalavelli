package it.parttimeteam.view.startup

sealed class StartupViewEvent

// Startup ViewEvent
case class PublicGameSubmitViewEvent(username: String, playersNumber: Int) extends StartupViewEvent

case class PrivateGameSubmitViewEvent(username: String, privateCode: String) extends StartupViewEvent

case class CreatePrivateGameSubmitViewEvent(username: String, playersNumber: Int) extends StartupViewEvent

case object LeaveLobbyViewEvent extends StartupViewEvent

case object RetryServerConnection extends StartupViewEvent



