package it.parttimeteam.controller.game

import com.sun.javafx.tk.Toolkit.Task

trait TurnTimer {
  def start()
  def end()
}

trait TurnTimerListener {
  def onStart()

  def onEnd()

  def onTick()
}

object TurnTimer {

  class TurnTimerImpl(listener: TurnTimerListener) extends TurnTimer {


    override def start(): Unit = {

    }

    override def end(): Unit = {

    }
  }

}


