package scolor.screens.Home

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}

object Home:
  def pageElement(maybeLevelBus: EventBus[Option[Int]]): Element =
    div(
      cls := "flex items-center justify-center min-h-[100vh] bg-gradient-to-bl from-violet-500 to-fuchsia-800",
      div(
        cls := "relative",
        h1(
          cls := "text-[80px] rotate-[-5deg] text-yellow-500",
          "Scolor",
        ),
        div(
          cls := "absolute right-0 text-white",
          button(
            "Go!",
            onClick.mapTo(Some(1)) --> maybeLevelBus,
          ),
        ),
      ),
    )
  end pageElement
end Home
