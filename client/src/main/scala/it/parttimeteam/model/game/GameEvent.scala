package it.parttimeteam.model.game

import it.parttimeteam.gamestate.PlayerGameState

sealed class GameEvent

case class StateUpdatedEvent(state: PlayerGameState) extends GameEvent



