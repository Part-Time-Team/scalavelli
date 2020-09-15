package it.parttimeteam.model.game

import it.parttimeteam.model.ErrorEvent

sealed class GameEvent

case class StateUpdatedEvent(state: ClientGameState) extends GameEvent

case class OpponentInTurnEvent(actualPlayerName: String) extends GameEvent

case object InTurnEvent extends GameEvent

case class InfoEvent(message: String) extends GameEvent

case class GameErrorEvent(reason: ErrorEvent) extends GameEvent

case object GameWonEvent extends GameEvent

case class GameLostEvent(winnerName: String) extends GameEvent

case object GameEndedBecausePlayerLeft extends GameEvent

case object TurnEndedEvent extends GameEvent





