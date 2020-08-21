package it.parttimeteam.controller.game

import it.parttimeteam.view.game.GameViewEvent

trait GameListener {

  def onViewEvent(viewEvent: GameViewEvent): Unit
}
