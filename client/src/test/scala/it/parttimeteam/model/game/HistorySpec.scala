package it.parttimeteam.model.game

import org.scalatest.wordspec.AnyWordSpecLike

class HistorySpec extends AnyWordSpecLike {

  "a history" should {
    val one = 1
    val two = 2
    val three = 3

    "return none when first initialized" in {
      val intHistory = History[Int]()
      assertResult(None)(intHistory.getPresent)
    }

    "return the last added value" in {
      val intHistory = History[Int]()
      val firstUpdatedHistory = intHistory.setPresent(three)
      assertResult(Some(three))(firstUpdatedHistory.getPresent)
      val secondValue = 5
      assertResult(Some(secondValue))(firstUpdatedHistory.setPresent(secondValue).getPresent)
    }

    "return none if there is no previous value" in {
      val intHistory = History[Int]()
      assertResult(None)(intHistory.setPresent(three).previous()._1)
    }

    "return something if there is a previous value" in {
      val intHistory = History[Int]()
      assertResult(Some(three))(intHistory.setPresent(three).setPresent(4).previous()._1)
    }

    "return the next value" in {
      val initialValue = 5
      val intHistory = History[Int]().setPresent(initialValue)
      val firstUpdatedHistory = intHistory.setPresent(three).previous()._2.previous()._2
      assertResult(Some(initialValue))(firstUpdatedHistory.getPresent)
      assertResult(Some(three))(firstUpdatedHistory.next()._1)
    }

    "clear the next values if a value is set after going back" in {
      val intHistory = History[Int]().setPresent(5).setPresent(three)
      assertResult(None)(intHistory.previous()._2.setPresent(8).next()._1)
    }

    "reset itself, return no value after this operation" in {
      val history = History().setPresent(one).setPresent(two).setPresent(three).setPresent(4)
      assertResult(None)(history.clear().getPresent)
    }

    "clear the history maintaining only the first value" in {
      val history = History().setPresent(one).setPresent(two).setPresent(three).setPresent(4)
      assertResult(Some(one))(history.reset()._1)
    }

    "cannot get previous when history created" in {
      val intHistory = History[Int]()
      assert(!intHistory.canPrevious)
    }

    "cannot get previous when history has only 1 value" in {
      val intHistory = History[Int]().setPresent(5)
      assert(!intHistory.canPrevious)
    }

    "can get previous when history has more than 1 value" in {
      val intHistory = History[Int]().setPresent(5).setPresent(4)
      assert(intHistory.canPrevious)
    }

    "can get next when history has been undo" in {
      val intHistory = History[Int]().setPresent(5).setPresent(4).previous()._2
      assert(intHistory.canNext)
    }

    "cannot get next when history created" in {
      val intHistory = History[Int]()
      assert(!intHistory.canNext)
    }

    "cannot get next when history has only 1 value" in {
      val intHistory = History[Int]()
      assert(!intHistory.canNext)
    }

    "get the head value of the history" in {
      val hist = History[Int]().setPresent(one).setPresent(two)
      assertResult(Some(one))(hist.headOption)
    }
  }
}
