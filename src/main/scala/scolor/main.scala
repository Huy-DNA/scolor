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
  def currentPage(maybeLevelStream: EventStream[Option[Int]]): Signal[Element] =
    val maybeLevel = maybeLevelStream.startWith(None)
    maybeLevel.map {
      case None => Home.pageElement(maybeLevelStream)
      case Some(level) => Level.pageElement(level, maybeLevelStream)
    }
  def appElement(): Element =
    val maybeLevelStream = EventStream.fromValue(None)
    div(child <-- Main.currentPage(maybeLevelStream))
  end appElement
end Main
