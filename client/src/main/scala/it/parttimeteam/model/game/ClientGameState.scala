package it.parttimeteam.model.game

import it.parttimeteam.gamestate.PlayerGameState

case class ClientGameState(
                            playerGameState: PlayerGameState,
                            canUndo: Boolean,
                            canRedo: Boolean,
                            canReset: Boolean,
                            canDrawCard: Boolean)





