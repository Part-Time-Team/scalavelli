package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.gamestate.Opponent
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.utils.MachiavelliLabel
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{HBox, TilePane, VBox}

trait OtherPlayersPane extends TilePane {

  def setOtherPlayers(otherPlayers: Seq[Opponent]): Unit
}

object OtherPlayersPane {

  class OtherPlayersPaneImpl extends OtherPlayersPane {
    this.setPrefColumns(2)


    /** @inheritdoc*/
    override def setOtherPlayers(otherPlayers: Seq[Opponent]): Unit = {
      this.children.clear()
      this.hgap = ViewConfig.TILE_GAP
      this.vgap = ViewConfig.TILE_GAP

      for (player: Opponent <- otherPlayers) {
        val nameLabel = MachiavelliLabel(player.name)
        val cardsNumberLabel = MachiavelliLabel(player.cardsNumber.toString)

        val playerInfoContainer: VBox = new VBox()
        val cardInfoContainer: HBox = new HBox()

        val cardImage: ImageView = new ImageView(new Image("images/cards/backBlue.png"))
        cardImage.fitWidth = 20d
        cardImage.preserveRatio = true

        cardInfoContainer.children.addAll(cardImage, cardsNumberLabel)
        playerInfoContainer.children.addAll(nameLabel, cardInfoContainer)

        this.children.add(playerInfoContainer)
      }
    }
  }

}
