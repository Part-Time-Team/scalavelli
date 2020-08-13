package it.parttimeteam.model.game

/**
 * Exposes all the game functionalities
 */
trait GameService {

  def notifyUserAction(action: UserGameAction): Unit


}
