package scolor.screens.Level

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}

import scala.util.Random
import scolor.color.Color
import scolor.event.Event

object Level:
  val countdownSeconds = 4
  val startupMs = countdownSeconds * 1000
  val scorePreviewMs = 3000

  def pageElement(level: Int, winBus: EventBus[Unit], failBus: EventBus[Unit]): Element =
    val previewColor = Color.HSL(Random.nextDouble() / 2 + 0.3, Random.nextDouble / 2 + 0.3, Random.nextDouble / 1.5 + 0.2)
    val requiredAcc = Random.nextInt(35) + 40

    val pickedColorBus = new EventBus[Option[String]]
    val pickedColorSignal = pickedColorBus.events.startWith(None).map {
      case None => None
      case Some(s) => Color.parseRGB(s).map(Color.toHSL(_))
    }

    val playerAccuracySignal = pickedColorSignal.map { _.map(pickedColor => Math.round(Color.colorCloseness(pickedColor, previewColor) * 100)) }

    playerAccuracySignal.addObserver(Observer(
      onNext = {
        case None => None
        case Some(acc) => scala.scalajs.js.timers.setTimeout (scorePreviewMs) {
          if acc >= requiredAcc then
            winBus.writer.onNext(Some(()))
          else
            failBus.writer.onNext(Some(()))
          ()
        }
      }
    ))(new Owner{})

    div(
      cls := "min-w-[100vw] min-h-[100vh] bg-gradient-to-r from-cyan-600 to-blue-500 p-16",
      div(
        cls := "mx-auto w-[350px] md:w-[450px] lg:w-[60vw]",
        p(
          cls := "text-yellow-500 text-[30px] text-center",
          s"Level $level",
        ),
        div(
          cls := "flex flex-col lg:flex-row gap-6 lg:gap-32 mt-6 min-h-[75vh]", 
          div(
            cls := "flex-1",
            p(
              cls := "text-white text-[15px] mb-2",
              s"Required accuracy: $requiredAcc%",
            ),
            child <-- pickedColorSignal.map {
              case None => Level.previewPane(previewColor.asInstanceOf[Color.HSL])
              case Some(_) => Level.plainPane(previewColor.asInstanceOf[Color.HSL])
            },
          ),
          div(
            cls := "flex-1",
            p(
              cls := "text-white lg:text-right text-[15px] mb-2",
              span(
                "Your accuracy: ",
                text <-- playerAccuracySignal.map(_.map(_.toString()).getOrElse("???")),
                "%",
              ),
            ),
            child <-- pickedColorSignal.map {
              case None => Level.pickPane(pickedColorBus)
              case Some(pickedColor) => Level.plainPane(pickedColor.asInstanceOf[Color.HSL])
            },
          ),
        ),
      ),
    )
  end pageElement

  def plainPane(color: Color.HSL): Element =
    div(
      backgroundColor := s"hsl(${color.h * 360}, ${color.s * 100}%, ${color.l * 100}%)",
      cls := "bg-white drop-shadow-lg h-[30vh] lg:h-[100%] flex items-center justify-center",
    )

  def previewPane(previewColor: Color.HSL): Element =
    div(
      backgroundColor <-- Event.createValueTransition(
        startupMs,
        from = s"hsl(${previewColor.h * 360}, ${previewColor.s * 100}%, ${previewColor.l * 100}%)",
        to = "#555555",
      ),
      cls := "bg-white drop-shadow-lg h-[30vh] lg:h-[100%] flex items-center justify-center",
      child <-- Event.createValueTransition(
        startupMs,
        from = "",
        to = span(
          cls := "text-white text-[50px]",
          "?",
        ),
      ),
    )
  end previewPane

  def pickPane(pickedColorBus: EventBus[Option[String]]): Element = 
    div(
      backgroundColor <-- pickedColorBus.events.map(_.getOrElse("")),
      cls <-- Event.createValueTransition(
        startupMs,
        from = "text-[50px]",
        to = "text-[20px] cursor-pointer",
      ).map(_ + " bg-[#555555] drop-shadow-lg h-[30vh] lg:h-[100%] flex items-center justify-center"),
      onClick --> Observer(
        onNext = _ => dom.document.querySelector(".color-picker").asInstanceOf[dom.html.Element].click()
      ),
      onInput.mapToValue.map(Some(_)) --> pickedColorBus,
      child <-- Event.createPeriodicCounter(1000, 4, _ - 1).map(counter => 
        span(
          cls := "text-white",
          if counter > 0 then s"${counter}" else "Pick a color!",
        ),
      ),
      input(
        cls := "absolute opacity-0",
        cls := "color-picker",
        typ := "color",
        disabled <-- Event.createValueTransition(startupMs, from = true, to = false),
      ),
    )
  end pickPane
end Level
