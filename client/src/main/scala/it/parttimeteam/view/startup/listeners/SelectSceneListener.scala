package it.parttimeteam.view.startup.listeners

trait SelectSceneListener {

  def onSelectedPublicGame(): Unit

  def onSelectedPrivateGame(): Unit

  def onSelectedCreatePrivateGame(): Unit
}
