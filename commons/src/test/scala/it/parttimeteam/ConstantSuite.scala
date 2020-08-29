package it.parttimeteam

import org.scalatest.funsuite.AnyFunSuite

class ConstantSuite extends AnyFunSuite {
  test("Test constant values") {
    assertResult("ScalavelliServer")(Constants.Remote.SERVER_ACTOR_SYSTEM_NAME)
    assertResult("lobby")(Constants.Remote.SERVER_LOBBY_ACTOR_NAME)
    assertResult("localhost")(Constants.Remote.SERVER_ADDRESS)
    assertResult(5150)(Constants.Remote.SERVER_PORT)
  }
}

class GamePreferencesSuite extends AnyFunSuite {
  test("Test preferences values") {
    assertResult(2)(GamePreferences.MIN_PLAYERS_NUM)
    assertResult(6)(GamePreferences.MAX_PLAYERS_NUM)
  }
}
