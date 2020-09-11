package it.parttimeteam.core

sealed class GameError

object GameError {

  case object CombinationNotValid extends GameError

  case object HandNotContainCard extends  GameError

  case object NoCardInBoard extends  GameError

  case object NoValidTurnPlay extends  GameError
}
