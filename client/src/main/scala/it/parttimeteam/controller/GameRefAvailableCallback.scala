package it.parttimeteam.controller

trait GameRefAvailableCallback {

  def onDone(gameRef: String) : Unit
}
