package it.parttimeteam.model.game

import it.parttimeteam.gamestate.PlayerGameState

sealed class ServerGameEvent

case class StateUpdatedEvent(state: PlayerGameState) extends ServerGameEvent

case class OpponentInTurnEvent(actualPlayerName: String) extends ServerGameEvent

case object InTurnEvent extends ServerGameEvent

case class InfoEvent(message: String) extends ServerGameEvent

case class ErrorEvent(reason: String) extends ServerGameEvent

case object GameWonEvent extends ServerGameEvent

case class GameLostEvent(winnerName: String) extends ServerGameEvent

case class GameEndedWithErrorEvent(reason: String) extends ServerGameEvent

case object TurnEndedEvent extends ServerGameEvent



