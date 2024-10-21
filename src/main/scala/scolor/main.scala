package scolor

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import org.scalajs.dom

// import javascriptLogo from "/javascript.svg"
@js.native @JSImport("/javascript.svg", JSImport.Default)
val javascriptLogo: String = js.native

@main
def Scolor(): Unit =
  dom.document.querySelector("#app").innerHTML = s"""
    <div>
    </div>
  """
end Scolor
