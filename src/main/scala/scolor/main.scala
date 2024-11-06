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
  def appElement(): Element =
    Home.pageElement()
  end appElement
end Main
