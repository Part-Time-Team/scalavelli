package it.parttimeteam

import it.parttimeteam.Rank.{Ace, Eight, Five, Four, Jack, King, Nine, Queen, Seven, Six, Ten, Three, Two}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.funsuite.AnyFunSuite

class RankSuite extends AnyFunSuite {
  test("Check all ranks properties") {
    assert(Ace().name equals "Ace")
    assert(Ace().shortName equals "A")
    assert(Ace().value equals 14)

    // TODO: Add other rank tests.
  }

  test("Check all ranks") {
    assert(Rank.all equals List(Ace(), Two(), Three(), Four(), Five(), Six(), Seven(), Eight(), Nine(), Ten(), Jack(), Queen(), King()))
  }
}

class RankSpec extends AnyFunSpec {
  describe("Rank conversions") {
    it("Should be Ace") {
      assert(Rank string2rank "A" equals Ace())
    }

    // TODO: Add other rank tests.

    it("Should raise RuntimeException for s string") {
      assertThrows[RuntimeException] {
        Rank.string2rank("s")
      }
    }
  }
}