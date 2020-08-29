package it.parttimeteam.model.game

import org.scalatest.wordspec.AnyWordSpecLike

class HistorySpec extends AnyWordSpecLike {

  "a history" should {

    "return none when first initialized" in {
      val intHistory = History[Int]()
      assertResult(None)(intHistory.getPresent)
    }

    "return the last added value" in {
      val intHistory = History[Int]()
      val value = 3
      val firstUpdatedHistory = intHistory.setPresent(value)
      assertResult(Some(value))(firstUpdatedHistory.getPresent)
      val secondValue = 5
      assertResult(Some(secondValue))(firstUpdatedHistory.setPresent(secondValue).getPresent)
    }

    "return none if there is no previous value" in {
      val intHistory = History[Int]()
      val value = 3
      assertResult(None)(intHistory.setPresent(value).previous()._1)
    }

    "return something if there is a previous value" in {
      val intHistory = History[Int]()
      val value = 3
      assertResult(Some(value))(intHistory.setPresent(value).setPresent(4).previous()._1)
    }

    "return the next value" in {
      val initialValue = 5
      val intHistory = History[Int]().setPresent(initialValue)
      val value = 3
      val firstUpdatedHistory = intHistory.setPresent(value).previous()._2.previous()._2
      assertResult(Some(initialValue))(firstUpdatedHistory.getPresent)
      assertResult(Some(value))(firstUpdatedHistory.next()._1)
    }

    "clear the next values if a value is set after going back" in {
      val value = 3
      val intHistory = History[Int]().setPresent(5).setPresent(value)
      assertResult(None)(intHistory.previous()._2.setPresent(8).next()._1)
    }

    "reset itself, return no value after this operation" in {
      val history = History().setPresent(1).setPresent(2).setPresent(3).setPresent(4)
      assertResult(None)(history.clear().getPresent)
    }

    "clear the history maintaining only the first value" in {
      val firstValue = 1
      val history = History().setPresent(firstValue).setPresent(2).setPresent(3).setPresent(4)
      assertResult(Some(firstValue))(history.reset()._1)
    }


  }

}
