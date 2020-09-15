package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.utils.{ScalavelliButton, Strings}
import scalafx.geometry.Insets
import scalafx.scene.layout.HBox

/**
  * Pane which contains buttons which allows to play/pick cards during the player turn.
  */
trait ActionBar extends HBox with ActionGamePane {
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

trait ActionBarListener {

  /**
    * Clear selected cards
    */
  def clearSelection(): Unit

  /**
    * The player pick a combination from the game board.
    *
    * @param combinationId the CardCombination identifier picked by player
    */
  def pickCombination(combinationId: String): Unit

  /**
    * The player clicked the make combination button.
    */
  def makeCombination(): Unit

  /**
    * The player clicked the pick cards button.
    */
  def pickCards(): Unit

  /**
    * The player clicked the update combination button.
    */
  def updateCombination(): Unit

  /**
    * The player sorts his hand cards by suit.
    */
  def sortHandBySuit(): Unit

  /**
    * The player sorts his hand cards by rank.
    */
  def sortHandByRank(): Unit
}

object ActionBar {

  class ActionBarImpl(listener: ActionBarListener) extends ActionBar {
    this.spacing = ViewConfig.formSpacing
    this.padding = Insets(5)

    val combinationBtn = ScalavelliButton(Strings.MAKE_COMBINATION_BTN, () => listener.makeCombination())
    val clearHandSelectionBtn = ScalavelliButton(Strings.CLEAR_SELECTION_BTN, () => listener.clearSelection())
    val pickCardsBtn = ScalavelliButton(Strings.PICK_CARDS_BTN, () => listener.pickCards())
    val updateCombinationBtn = ScalavelliButton(Strings.UPDATE_COMBINATION_BTN, () => listener.updateCombination())
    val sortBySuitBtn = ScalavelliButton(Strings.SORT_SUIT_BTN, () => listener.sortHandBySuit())
    val sortByRankBtn = ScalavelliButton(Strings.SORT_RANK_BTN, () => listener.sortHandByRank())

    combinationBtn.setDisable(true)
    clearHandSelectionBtn.setDisable(true)
    pickCardsBtn.setDisable(true)
    updateCombinationBtn.setDisable(true)

    this.children.addAll(sortBySuitBtn, sortByRankBtn, combinationBtn, clearHandSelectionBtn, pickCardsBtn, updateCombinationBtn)

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
    override def enableActions(): Unit = {

    }
  }

}