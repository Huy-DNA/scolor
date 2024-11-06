package scolor.screens.Level

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}

object Level:
  def pageElement(level: Int, maybeLevelBus: EventBus[Option[Int]]): Element =
    val rand = new scala.util.Random
    val r = rand.nextInt(256).toHexString.reverse.padTo(2, "0").reverse
    val g = rand.nextInt(256).toHexString.reverse.padTo(2, "0").reverse
    val b = rand.nextInt(256).toHexString.reverse.padTo(2, "0").reverse
    div(
      cls := "flex items-center justify-center min-h-[100vh]",
      backgroundColor := s"#$r$g$b",
      p(
        cls := "text-[30px] text-white drop-shadow-md",
        s"Level $level"
      ),
    )
  end pageElement
end Level
