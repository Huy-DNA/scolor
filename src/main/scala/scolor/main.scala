package scolor

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}

import scolor.screens.Home.*
import scolor.screens.Level.*

@main
def Scolor(): Unit =
  renderOnDomContentLoaded(
    dom.document.getElementById("app"),
    Main.appElement()
  )
end Scolor

object Main:
  def currentPage(maybeLevelBus: EventBus[Option[Int]]): Signal[Element] =
    val maybeLevelStream = maybeLevelBus.events
    val maybeLevel = maybeLevelStream.startWith(None)
    maybeLevel.map {
      case None => Home.pageElement(maybeLevelBus)
      case Some(level) => Level.pageElement(level, maybeLevelBus)
    }
  end currentPage

  def appElement(): Element =
    val maybeLevelBus = new EventBus[Option[Int]]
    div(child <-- Main.currentPage(maybeLevelBus))
  end appElement
end Main
