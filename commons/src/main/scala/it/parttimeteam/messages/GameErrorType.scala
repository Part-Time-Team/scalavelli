package it.parttimeteam.messages

sealed class GameErrorType

case object InvalidUserIdError extends GameErrorType

case object InvalidPlaysError extends GameErrorType
