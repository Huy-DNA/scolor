package scolor

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}
import com.raquo.airstream.ownership.Owner

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
    val maybeLevelBus = new EventBus[Option[Int]]
    val maybeLevel = maybeLevelBus.events.startWith(None)
    
    div(child <-- maybeLevel.map {
      case None => {
        val enterGameBus = new EventBus[Option[Unit]]
        enterGameBus.events.addObserver(Observer(
          onNext = _ => maybeLevelBus.writer.onNext(Some(1))
        ))(new Owner{})
        Home.pageElement(enterGameBus)
      }
      case Some(level) => Level.pageElement(level, maybeLevelBus)
    })
  end appElement
end Main
