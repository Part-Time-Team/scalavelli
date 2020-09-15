package it.parttimeteam.view.startup.listeners

/**
  * Action which the user can make into SelectScene
  */
trait SelectSceneListener {

  /**
    * The user wants to join a public game
    */
  def onSelectedPublicGame(): Unit

  /**
    * The user wants to join a private game
    */
  def onSelectedPrivateGame(): Unit

  /**
    * The user wants to create a private game
    */
  def onSelectedCreatePrivateGame(): Unit
}
