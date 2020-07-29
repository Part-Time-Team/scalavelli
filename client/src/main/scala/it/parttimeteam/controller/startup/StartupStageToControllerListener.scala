package it.parttimeteam.controller.startup

trait StartupStageToControllerListener {
  def requestGameWithPlayers(username: String, playersNumber: Int): Unit

  def requestPrivateGame(): Unit

  def createPrivateGame() : Unit
}
