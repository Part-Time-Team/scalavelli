package it.parttimeteam.messages

sealed class GameError

case object InvalidUserIdError extends GameError

case object InvalidPlaysError extends GameError

case class PlayerActionNotValidError(reason: String) extends GameError
