package it.parttimeteam.model

import it.parttimeteam.core.GameError

sealed class ErrorEvent

object ErrorEvent {

  // TODO inserire i vari errori generabili dal model verso i controller

  case class GenericError(reason: String) extends ErrorEvent

  case object ServerNotFound extends ErrorEvent

  case object NoValidTurnPlay extends ErrorEvent

  case object CombinationNotValid extends ErrorEvent

  case object HandNotContainCard extends ErrorEvent

  case object NoCardInBoard extends ErrorEvent

  /**
   * Function 
   *
   * @param error error GameError
   * @return ErrorEvent
   */
  def mapError(error : GameError) : ErrorEvent = error match {
    case GameError.CombinationNotValid => this.CombinationNotValid
    case GameError.HandNotContainCard => this.HandNotContainCard
    case GameError.NoCardInBoard => this.NoCardInBoard
    case _ => this.GenericError("Unknown error")
  }
}
