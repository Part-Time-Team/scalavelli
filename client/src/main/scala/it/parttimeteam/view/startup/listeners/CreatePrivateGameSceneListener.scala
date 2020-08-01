package it.parttimeteam.view.startup.listeners

trait CreatePrivateGameSceneListener extends BaseStartUpSceneListener{
  def createPrivateGame(username: String, playersNumber: Int): Unit
}
