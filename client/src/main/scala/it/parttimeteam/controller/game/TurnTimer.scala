package it.parttimeteam.controller.game


import java.util.{Timer, TimerTask}

trait TurnTimer {
  def start()

  def end()
}

trait TurnTimerListener {
  def onStart()

  def onEnd()

  def onTick(millis: Long)
}

object TurnTimer {

  class TurnTimerImpl(duration: Long, listener: TurnTimerListener) extends TurnTimer {
    var timer: Timer = new Timer()
    var timeRemaining: Long = duration * 1000

    var tickTask: TimerTask = _
    var endTask: TimerTask = _

    override def start(): Unit = {
      tickTask = new TimerTask {
        override def run(): Unit = {
          timeRemaining = timeRemaining - 1000
          listener.onTick(timeRemaining)
        }
      }

      endTask = new TimerTask {
        override def run(): Unit = {
          end()
          listener.onEnd()
        }
      }

      timer.schedule(tickTask, 1000, 1000)
      timer.schedule(endTask, duration * 1000)
      listener.onStart()
    }

    override def end(): Unit = {
      tickTask.cancel()
      endTask.cancel()
      timer.purge()
      timeRemaining = duration * 1000
    }
  }

}


