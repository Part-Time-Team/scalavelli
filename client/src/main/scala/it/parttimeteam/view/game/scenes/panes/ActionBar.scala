package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.view.game.listeners.GameSceneToStageListener
import it.parttimeteam.view.utils.MachiavelliButton
import scalafx.scene.layout.HBox

trait ActionBar extends HBox {
  def enableMakeCombination(enable: Boolean): Unit

  def enablePickCards(enable: Boolean): Unit

  def enableClearHandSelection(enable: Boolean): Unit
}

object ActionBar {

  class ActionBarImpl(listener: GameSceneToStageListener) extends ActionBar {

    val combinationBtn = MachiavelliButton("Make Combination", () => listener.makeCombination())
    val clearHandSelectionBtn = MachiavelliButton("Clear Selection", () => listener.clearHandSelection())
    val pickCardsBtn = MachiavelliButton("Pick Cards", () => listener.pickCards())

    combinationBtn.setDisable(true)
    clearHandSelectionBtn.setDisable(true)
    pickCardsBtn.setDisable(true)

    this.children.addAll(combinationBtn, clearHandSelectionBtn, pickCardsBtn)

    override def enableMakeCombination(enable: Boolean): Unit = combinationBtn.setDisable(!enable)

    override def enablePickCards(enable: Boolean): Unit = pickCardsBtn.setDisable(!enable)

    override def enableClearHandSelection(enable: Boolean): Unit = clearHandSelectionBtn.setDisable(!enable)
  }

}