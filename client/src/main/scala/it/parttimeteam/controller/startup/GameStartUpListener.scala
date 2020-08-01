package it.parttimeteam.controller.startup

trait GameStartUpListener {

  def requestGameWithPlayers(username: String, playersNumber: Int): Unit

  def requestPrivateGame(username: String, code: String): Unit

  def createPrivateGame(username: String, playersNumber: Int) : Unit
}
