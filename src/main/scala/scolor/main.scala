package scolor

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}

@main
def Scolor(): Unit =
  dom.document.querySelector("#app").innerHTML = s"""
    <div>
    </div>
  """
end Scolor
