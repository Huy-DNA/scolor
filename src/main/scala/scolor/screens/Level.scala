package scolor.screens.Level

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}

object Level:
  def previewPane(h: Int, s: Int, l: Int): Element =
    div(
      backgroundColor := s"hsl($h, $s%, $l%)",
      cls := "bg-white drop-shadow-lg h-[100%]",
    )
  end previewPane

  def pickPane(playerEventBus: EventBus[Option[Float]]): Element =
    div(
      cls := "bg-white drop-shadow-lg h-[100%]",
    )
  end pickPane

  def pageElement(level: Int, maybeLevelBus: EventBus[Option[Int]]): Element =
    val rand = new scala.util.Random
    val h = rand.nextInt(361)
    val s = rand.nextInt(71) + 30
    val l = rand.nextInt(91) + 10
    val acc = rand.nextInt(35) + 40
    val delaySignal = EventStream.delay(4000).map(_ => Some(())).startWith(None)
    val playerAccuracyBus = new EventBus[Option[Float]]
    div(
      cls := "min-w-[100vw] min-h-[100vh] bg-gradient-to-r from-cyan-600 to-blue-500 p-16",
      div(
        cls := "mx-auto w-[350px] md:w-[450px] lg:w-[60vw]",
        p(
          cls := "text-yellow-500 text-[30px] text-center",
          s"Level $level",
        ),
        div(
          cls := "flex sm:flex-col lg:flex-row sm:gap-16 lg:gap-32 mt-6 min-h-[75vh]", 
          div(
            cls := "flex-1",
            p(
              cls := "text-white text-[15px] mb-2",
              s"Required accuracy: $acc%",
            ),
            Level.previewPane(h, s, l),
          ),
          div(
            cls := "flex-1",
            p(
              cls := "text-white text-right text-[15px] mb-2",
              s"Your accuracy: ??%",
            ),
            Level.pickPane(playerAccuracyBus),
          ),
        ),
      ),
    )
  end pageElement
end Level
