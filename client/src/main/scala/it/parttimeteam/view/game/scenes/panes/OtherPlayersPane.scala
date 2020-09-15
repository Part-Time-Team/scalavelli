package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.gamestate.Opponent
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.utils.ScalavelliLabel
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{HBox, TilePane, VBox}

/**
  * Pane where other players info are displayed
  */
trait OtherPlayersPane extends VBox {

  /**
    * Sets the other players info inside the pane
    *
    * @param otherPlayers other players info
    */
  def setOtherPlayers(otherPlayers: Seq[Opponent]): Unit
}

object OtherPlayersPane {

  class OtherPlayersPaneImpl extends OtherPlayersPane {
    var label = ScalavelliLabel("Other players:")
    label.getStyleClass.add("boldText")
    var pane: TilePane = new TilePane()
    pane.setPrefColumns(2)

    this.prefWidth = 200d
    this.children.addAll(label, pane)


    /** @inheritdoc*/
    override def setOtherPlayers(otherPlayers: Seq[Opponent]): Unit = {
      pane.children.clear()
      pane.hgap = ViewConfig.TILE_GAP
      pane.vgap = ViewConfig.TILE_GAP

      for (player: Opponent <- otherPlayers) {
        val nameLabel = ScalavelliLabel(player.name)
        val cardsNumberLabel = ScalavelliLabel(player.cardsNumber.toString)

        val playerInfoContainer: VBox = new VBox()
        val cardInfoContainer: HBox = new HBox()

        val cardImage: ImageView = new ImageView(new Image("images/cards/backBlue.png"))
        cardImage.fitWidth = 20d
        cardImage.preserveRatio = true

        cardInfoContainer.children.addAll(cardImage, cardsNumberLabel)
        playerInfoContainer.children.addAll(nameLabel, cardInfoContainer)

        pane.children.add(playerInfoContainer)
      }
    }
  }

}
