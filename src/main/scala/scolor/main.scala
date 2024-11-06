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
  def currentPage(maybeLevel: Signal[Option[Int]]): Signal[Element] =
    maybeLevel.map {
      case None => Home.pageElement()
      case Some(level) => Level.pageElement(level)
    }
  def appElement(): Element =
    val maybeLevelStream = EventStream.fromValue(None)
    val maybeLevel = maybeLevelStream.startWith(None)
    div(child <-- Main.currentPage(maybeLevel))
  end appElement
end Main
