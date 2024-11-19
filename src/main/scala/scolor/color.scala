package scolor.color

import scala.util.matching.Regex

enum Color:
  // r, g, b must be in [0, 1]
  case RGB(r: Double, g: Double, b: Double)
  // h, s, l must be in [0, 1]
  case HSL(h: Double, s: Double, l: Double)

object Color:
  def parseRGB(hex: String): Option[Color] =
    val colorRegex: Regex = "^#([0-9a-fA-F]){6}$".r
    colorRegex.findFirstMatchIn(hex).map(_ => {
      val r = Integer.parseInt(hex.slice(1, 3).toLowerCase(), 16) / 256.0
      val g = Integer.parseInt(hex.slice(3, 5).toLowerCase(), 16) / 256.0
      val b = Integer.parseInt(hex.slice(5, 7).toLowerCase(), 16) / 256.0
      Color.RGB(r, g, b)
    })
  end parseRGB 

  def toRGB(color: Color): Color =
    def hueToRGB(p: Double, q: Double, t: Double): Double =
      val _t = if t < 0 then t + 1 else if t > 1 then t - 1 else t
      if _t < 1.0/6 then p + (q - p) * 6 * _t
      else if _t < 1.0/2 then q
      else if _t < 2.0/3 then p + (q - p) * (2.0/3 - _t) * 6
      else p
    color match
      case Color.RGB(_, _, _) => color
      case Color.HSL(h, s, l) => if s == 0 then Color.RGB(l, l, l) else {
        val q = if l < 0.5 then l * (1 + s) else 1 + s - l * s
        val p = 2 * l - q
        Color.RGB(
          hueToRGB(p, q, h + 1.0/3),
          hueToRGB(p, q, h),
          hueToRGB(p, q, h - 1.0/3),
        )
      }
  end toRGB

  def toHSL(color: Color): Color = color match
    case Color.HSL(_, _, _) => color
    case Color.RGB(r, g, b) => {
      val vmax = r.max(g).max(b)
      val vmin = r.min(g).min(b)
      val l = (vmax + vmin) / 2

      if vmax == vmin then return Color.HSL(0, 0, l)
      val d = vmax - vmin
      val s = if l > 0.5 then d / (2 - vmax - vmin) else d / (vmax + vmin)
      val h = (
        if vmax == r then (g - b) / d + (if g < b then 6 else 0)
        else if vmax == g then (b - r) / d + 2
        else (r - g) / d + 4
      ) / 6
      Color.HSL(h, s, l)
    }
  end toHSL
end Color
