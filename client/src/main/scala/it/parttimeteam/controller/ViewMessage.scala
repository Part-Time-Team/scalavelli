package it.parttimeteam.controller

sealed class ViewMessage

object ViewMessage {

  case class ActualPlayerTurn(playerUsername: String) extends ViewMessage

  case object YourTurn extends ViewMessage

}