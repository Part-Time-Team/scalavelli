package it.parttimeteam.view.startup.listeners

trait PlayersGameSceneListener extends BaseStartUpSceneListener{
  def registerToGame(username: String, playersNumber: Int): Unit
}
