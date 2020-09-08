package it.parttimeteam.model.game

import it.parttimeteam.controller.game.UserGameAction

/**
  * Exposes all the game functionalities
  */
trait GameService {

  def playerReady(): Unit

  def notifyUserAction(action: UserGameAction): Unit
}
