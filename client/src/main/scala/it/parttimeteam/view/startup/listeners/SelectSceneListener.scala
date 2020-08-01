package it.parttimeteam.view.startup.listeners

trait SelectSceneListener {

  def onSelectedGameWithPlayers(): Unit

  def onSelectedGameWithCode(): Unit

  def onSelectedCreatePrivateGame(): Unit
}
