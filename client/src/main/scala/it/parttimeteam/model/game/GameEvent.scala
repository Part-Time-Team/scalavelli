package it.parttimeteam.model.game

import it.parttimeteam.gamestate.PlayerGameState

sealed class GameEvent

case class StateUpdatedEvent(state: PlayerGameState) extends GameEvent

case class TurnChangedEvent(state: PlayerGameState, actualPlayerName: String) extends GameEvent

case class YourTurnEvent(state: PlayerGameState) extends GameEvent



