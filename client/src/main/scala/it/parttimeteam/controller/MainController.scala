package it.parttimeteam.controller

trait MainController {
  def startGame(gameRef: String): Unit

  def startUp(gameAvailable: GameRefAvailableCallback): Unit
}
