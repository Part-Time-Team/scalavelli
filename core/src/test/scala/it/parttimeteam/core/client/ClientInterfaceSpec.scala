package it.parttimeteam.core.client

import it.parttimeteam.Hand
import it.parttimeteam.core.ManagerBuilder
import it.parttimeteam.core.player.Player
import org.scalatest.funspec.AnyFunSpec

class ClientInterfaceSpec extends AnyFunSpec {

  describe("A Client Game Interface") {
    describe("At inizialization") {
      it("Must contain an empty player") {
        val client = ManagerBuilder().addPlayer(Player.HandPlayer("Daniele", "1", Hand())).build()
        // TODO: Complete tests.
        //val player: Player = client.
        // assert(player equals Player.EmptyPlayer())
      }
    }
  }
}
