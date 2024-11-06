package scolor.screens.Level

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}

object Level:
  def pageElement(level: Int, maybeLevelBus: EventBus[Option[Int]]): Element =
    val rand = new scala.util.Random
    val h = rand.nextInt(360)
    val s = rand.nextInt(101)
    val l = rand.nextInt(101)
    div(
      cls := "flex items-center justify-center min-h-[100vh]",
      backgroundColor := s"hsl($h, $s%, $l%)",
      p(
        cls := "text-[30px] text-white drop-shadow-md",
        s"Level $level"
      ),
    )
  end pageElement
end Level
