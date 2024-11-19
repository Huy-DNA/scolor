package scolor.screens.Level

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}

object Level:
  def previewPane(h: Int, s: Int, l: Int): Element =
    val delaySignal = EventStream.delay(4000).map(_ => Some(())).startWith(None)
    div(
      backgroundColor <-- delaySignal.map {
        case None => s"hsl($h, $s%, $l%)"
        case Some(_) => "#555555"
      },
      cls := "bg-white drop-shadow-lg h-[30vh] lg:h-[100%] flex items-center justify-center",
      child <-- delaySignal.map {
        case None => ""
        case Some(_) => span(
          cls := "text-white text-[50px]",
          "?",
        )
      },
    )
  end previewPane

  def pickPane(playerEventBus: EventBus[Option[Float]]): Element =
    val delaySignal = EventStream.delay(4000).map(_ => Some(())).startWith(None)
    var sec = 4
    val periodicSignal = EventStream.periodic(1000).map(_ => {
      sec = sec - 1
      sec + 1
    })
    div(
      backgroundColor := "#555555",
      cls := "bg-white drop-shadow-lg h-[30vh] lg:h-[100%] flex items-center justify-center",
      cls <-- delaySignal.map {
        case None => "text-[50px]"
        case Some(_) => "text-[20px] cursor-pointer"
      },
      onClick --> Observer(
        onNext = _ => dom.document.querySelector(".color-picker").asInstanceOf[dom.html.Element].click()
      ),
      child <-- periodicSignal.map(sec => 
        span(
          cls := "text-white",
          if sec > 0 then s"$sec" else "Pick a color!",
        )
      ),
      input(
        cls := "absolute opacity-0",
        cls := "color-picker",
        typ := "color",
        disabled <-- delaySignal.map(_.isEmpty),
      ),
    )
  end pickPane

  def pageElement(level: Int, maybeLevelBus: EventBus[Option[Int]]): Element =
    val rand = new scala.util.Random
    val h = rand.nextInt(301) + 20
    val s = rand.nextInt(71) + 30
    val l = rand.nextInt(61) + 30
    val acc = rand.nextInt(35) + 40
    val playerAccuracyBus = new EventBus[Option[Float]]
    val playerAccuracySignal = playerAccuracyBus.events.startWith(None)
    div(
      cls := "min-w-[100vw] min-h-[100vh] bg-gradient-to-r from-cyan-600 to-blue-500 p-16",
      div(
        cls := "mx-auto w-[350px] md:w-[450px] lg:w-[60vw]",
        p(
          cls := "text-yellow-500 text-[30px] text-center",
          s"Level $level",
        ),
        div(
          cls := "flex sm:flex-col lg:flex-row gap-6 lg:gap-32 mt-6 min-h-[75vh]", 
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
              cls := "text-white lg:text-right text-[15px] mb-2",
              span(
                text <-- playerAccuracySignal.map(
                  maybeAccuracy => maybeAccuracy match
                    case None => s"Your accuracy: ???%"
                    case Some(acc) => s"Your accuracy: $acc%"
                ),
              ),
            ),
            Level.pickPane(playerAccuracyBus),
          ),
        ),
      ),
    )
  end pageElement
end Level
