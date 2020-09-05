package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.utils.MachiavelliButton
import scalafx.geometry.Insets
import scalafx.scene.layout.HBox

/**
  * Pane which contains buttons which allows to play/pick cards during the player turn.
  */
trait ActionBar extends HBox with BaseGamePane {
  /**
    * Enable/Disable the action for updating a CardCombination.
    *
    * @param enable if the action should be enabled or not
    */
  def enableUpdateCardCombination(enable: Boolean): Unit

  /**
    * Enable/Disable the action for playing a CardCombination.
    *
    * @param enable if the action should be enabled or not
    */
  def enableMakeCombination(enable: Boolean): Unit

  /**
    * Enable/Disable the action for picking Cards from the game board.
    *
    * @param enable if the action should be enabled or not
    */
  def enablePickCards(enable: Boolean): Unit

  /**
    * Enable/Disable the action for clear the selection made by player.
    *
    * @param enable if the action should be enabled or not
    */
  def enableClearHandSelection(enable: Boolean): Unit
}

object ActionBar {

  class ActionBarImpl(listener: GameSceneListener) extends ActionBar {
    this.spacing = ViewConfig.formSpacing
    this.padding = Insets(5)

    val combinationBtn = MachiavelliButton("Make Combination", () => listener.makeCombination())
    val clearHandSelectionBtn = MachiavelliButton("Clear Selection", () => listener.clearHandSelection())
    val pickCardsBtn = MachiavelliButton("Pick Cards", () => listener.pickCards())
    val updateCombinationBtn = MachiavelliButton("Update Combination", () => listener.updateCombination())

    combinationBtn.setDisable(true)
    clearHandSelectionBtn.setDisable(true)
    pickCardsBtn.setDisable(true)
    updateCombinationBtn.setDisable(true)

    this.children.addAll(combinationBtn, clearHandSelectionBtn, pickCardsBtn, updateCombinationBtn)

    /** @inheritdoc */
    override def enableMakeCombination(enable: Boolean): Unit = combinationBtn.setDisable(!enable)

    /** @inheritdoc */
    override def enablePickCards(enable: Boolean): Unit = pickCardsBtn.setDisable(!enable)

    /** @inheritdoc */
    override def enableClearHandSelection(enable: Boolean): Unit = clearHandSelectionBtn.setDisable(!enable)

    /** @inheritdoc */
    override def enableUpdateCardCombination(enable: Boolean): Unit = updateCombinationBtn.setDisable(!enable)

    /** @inheritdoc */
    override def disableActions(): Unit = {
      combinationBtn.setDisable(true)
      clearHandSelectionBtn.setDisable(true)
      pickCardsBtn.setDisable(true)
      updateCombinationBtn.setDisable(true)
    }

    /** @inheritdoc */
    override def enableActions(): Unit = {}
  }

}