package it.parttimeteam.model.game

import it.parttimeteam.view.game.GameViewEvent

/**
 * Exposes all the game functionalities
 */
trait GameService {

  def playerReady(): Unit

  // TODO rename gameview event into UserGameAction or similar, remove view reference in name
  def notifyUserAction(action: GameViewEvent): Unit

}
