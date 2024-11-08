package scolor.screens.Level

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}

object Level:
  def previewPane(): Element =
    div(
      cls := "flex-1 bg-white",
    )
  end previewPane

  def pickPane(): Element =
    div(
      cls := "flex-1 bg-white",
    )
  end pickPane

  def pageElement(level: Int, maybeLevelBus: EventBus[Option[Int]]): Element =
    val rand = new scala.util.Random
    val h = rand.nextInt(361)
    val s = rand.nextInt(101)
    val l = rand.nextInt(101)
    val delaySignal = EventStream.delay(4000).map(_ => Some(())).startWith(None)
    div(
      cls := "min-w-[100vw] min-h-[100vh] bg-gradient-to-r from-cyan-600 to-blue-500 p-16",
      div(
        cls := "mx-auto w-[350px] md:w-[450px] lg:w-[60vw]",
        p(
          cls := "text-yellow-500 text-[30px] text-center",
          s"Level $level",
        ),
        div(
          cls := "flex sm:flex-col lg:flex-row gap-16 mt-10 min-h-[75vh]", 
          Level.previewPane(),
          Level.pickPane(),
        ),
      ),
    )
  end pageElement
end Level
