package it.parttimeteam.gamestate

import it.parttimeteam.core.player.Player

sealed class GameState(val players: List[Player])

object GameState{
  case class GlobalGameState(override val players: List[Player]) extends GameState(players)
  case class LocalGameState(override val players: List[Player]) extends GameState(players)
}
