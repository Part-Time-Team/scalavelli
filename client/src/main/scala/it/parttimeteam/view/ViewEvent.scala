package it.parttimeteam.view

sealed class ViewEvent {

}

case class PublicGameSubmitViewEvent(username: String, playersNumber: Int) extends ViewEvent

case class PrivateGameSubmitViewEvent(username: String, privateCode: String) extends ViewEvent

case class CreatePrivateGameSubmitViewEvent(username: String, playersNumber: Int) extends ViewEvent
