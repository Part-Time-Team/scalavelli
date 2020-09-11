package it.parttimeteam.model

sealed class ErrorEvent

object ErrorEvent {

  // TODO inserire i vari errori generabili dal model verso i controller

  case class GenericError(reason: String) extends ErrorEvent

  case object ServerNotFound extends ErrorEvent
  

}
