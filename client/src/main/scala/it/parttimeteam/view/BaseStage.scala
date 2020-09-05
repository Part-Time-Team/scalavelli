package it.parttimeteam.view

import it.parttimeteam.Constants
import scalafx.application.JFXApp

class BaseStage extends JFXApp.PrimaryStage {
  title = Constants.Client.GAME_NAME
  resizable = true
  width = ViewConfig.screenWidth
  height = ViewConfig.screenHeight
}
