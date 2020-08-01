package it.parttimeteam.view.startup.listeners

trait PrivateGameSceneListener extends BaseStartUpSceneListener{
  def registerToPrivateGame(username: String, privateCode: String)
}
