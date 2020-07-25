package it.parttimeteam.controller.startup

trait StartupStageToControllerListener {

  def requestGameWithPlayers(): Unit

  def requestPrivateGame(): Unit

  def createPrivateGame() : Unit
}
