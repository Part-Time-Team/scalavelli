package it.parttimeteam.model.game

/**
 * Exposes all the game functionalities
 */
trait GameService {

  def playerReady(): Unit

  def notifyUserAction(action: UserGameAction): Unit

}
