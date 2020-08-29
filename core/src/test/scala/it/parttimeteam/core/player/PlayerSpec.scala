package it.parttimeteam.core.player

import it.parttimeteam.core.collections.Hand
import org.scalatest.funspec.AnyFunSpec

class PlayerSpec extends AnyFunSpec {
  describe("A player") {
    describe("Made full") {
      val player = Player("Lorenzo", "#001", Hand(Nil, Nil))
      it("Have a name") {
        assert(player.name equals "Lorenzo")
      }

      it("Have an id") {
        assert(player.id equals "#001")
      }

      it("Have an hand") {
        assert(player.hand equals Hand(Nil, Nil))
      }
    }
  }
}
