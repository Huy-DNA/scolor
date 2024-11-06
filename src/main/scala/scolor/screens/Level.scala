package scolor.screens.Level

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}

object Level:
  def pageElement(level: Int, maybeLevelBus: EventBus[Option[Int]]): Element =
    div(
      cls := "flex items-center justify-center min-h-[100vh]",
      p(
        cls := "text-[30px] text-white drop-shadow-md",
        s"Level $level"
      ),
    )
  end pageElement
end Level
