package scolor.screens.Level

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}

object Level:
  def colorView(level: Int, h: Int, s: Int, l: Int): Element =
    val th = (h + 180) % 360
    val ts = (h + 50) % 100
    val tl = (h + 50) % 100
    
    div(
      cls := "flex items-center justify-center min-h-[100vh]",
      backgroundColor := s"hsl($h, $s%, $l%)",
      p(
        cls := "text-[30px] drop-shadow-md",
        color := s"hsl($th, $ts%, $tl%)",
        s"Level $level",
      ),
    )
  end colorView

  def pageElement(level: Int, maybeLevelBus: EventBus[Option[Int]]): Element =
    val rand = new scala.util.Random
    val h = rand.nextInt(361)
    val s = rand.nextInt(101)
    val l = rand.nextInt(101)
    colorView(level, h, s, l)
  end pageElement
end Level
