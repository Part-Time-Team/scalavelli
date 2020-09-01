package it.parttimeteam.messages

sealed class ErrorMessage

case object InvalidUserIdError extends ErrorMessage

case object InvalidPlaysError extends ErrorMessage

case class PlayerActionNotValidError(reason: String) extends ErrorMessage

case object PrivateLobbyIdNotValidError extends ErrorMessage


