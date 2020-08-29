package it.parttimeteam.model.game

import it.parttimeteam.gamestate.PlayerGameState

sealed class GameEvent

case class StateUpdatedEvent(state: PlayerGameState) extends GameEvent

case class OpponentInTurnEvent(actualPlayerName: String) extends GameEvent

case object InTurnEvent extends GameEvent

case class InfoEvent(message: String) extends GameEvent

case class ErrorEvent(reason: String) extends GameEvent

case object GameWonEvent extends GameEvent

case class GameLostEvent(winnerName: String) extends GameEvent

case class GameEndedWithErrorEvent(reason: String) extends GameEvent

case object TurnEndedEvent extends GameEvent



