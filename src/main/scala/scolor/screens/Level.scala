package scolor.screens.Level

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}

object Level:
  def pageElement(level: Int, maybeLevelBus: EventBus[Option[Int]]): Element =
    val rand = new scala.util.Random
    val h = rand.nextInt(361)
    val s = rand.nextInt(101)
    val l = rand.nextInt(101)
    val delaySignal = EventStream.delay(4000).map(_ => Some(())).startWith(None)
    div()
  end pageElement
end Level
