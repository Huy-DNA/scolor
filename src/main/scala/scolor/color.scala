package scolor.color

import scala.util.matching.Regex

enum Color:
  // r, g, b must be in [0, 1]
  case RGB(r: Double, g: Double, b: Double)
  // h, s, l must be in [0, 1]
  case HSL(h: Double, s: Double, l: Double)
  // y, u, v must be in [0, 1]
  case YUV(y: Double, u: Double, v: Double)

object Color:
  def parseRGB(hex: String): Option[Color.RGB] =
    val colorRegex: Regex = "^#([0-9a-fA-F]){6}$".r
    colorRegex.findFirstMatchIn(hex).map(_ => {
      val r = Integer.parseInt(hex.slice(1, 3).toLowerCase(), 16) / 255.0
      val g = Integer.parseInt(hex.slice(3, 5).toLowerCase(), 16) / 255.0
      val b = Integer.parseInt(hex.slice(5, 7).toLowerCase(), 16) / 255.0
      Color.RGB(r, g, b)
    })
  end parseRGB 

  def toRGB(color: Color): Color.RGB =
    def hueToRGB(p: Double, q: Double, t: Double): Double =
      val _t = if t < 0 then t + 1 else if t > 1 then t - 1 else t
      if _t < 1.0/6 then p + (q - p) * 6 * _t
      else if _t < 1.0/2 then q
      else if _t < 2.0/3 then p + (q - p) * (2.0/3 - _t) * 6
      else p
    color match
      case Color.RGB(_, _, _) => color.asInstanceOf[Color.RGB]
      case Color.HSL(h, s, l) => if s == 0 then Color.RGB(l, l, l) else {
        val q = if l < 0.5 then l * (1 + s) else l + s - l * s
        val p = 2 * l - q
        Color.RGB(
          hueToRGB(p, q, h + 1.0/3),
          hueToRGB(p, q, h),
          hueToRGB(p, q, h - 1.0/3),
        )
      }
      case Color.YUV(y, u, v) => {
        val r = y + 1.13983 * v
        val g = y - 0.39465 * u - 0.58060 * v
        val b = y + 2.03211 * u
        def clamp(value: Double): Double = Math.max(0.0, Math.min(1.0, value))
        Color.RGB(clamp(r), clamp(g), clamp(b))
      }
  end toRGB

  def toHSL(color: Color): Color.HSL = color match
    case Color.HSL(_, _, _) => color.asInstanceOf[Color.HSL]
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
    case Color.YUV(_, _, _) => Color.toHSL(Color.toRGB(color))
  end toHSL

  def toYUV(color: Color): Color.YUV = color match
    case Color.HSL(_, _, _) => Color.toYUV(Color.toRGB(color))
    case Color.RGB(r, g, b) => {
      val y = 0.299 * r + 0.587 * g + 0.114 * b
      val u = -0.14713 * r - 0.28886 * g + 0.436 * b
      val v = 0.615 * r - 0.51499 * g - 0.1001 * b
      def clamp(value: Double): Double = Math.max(0.0, Math.min(1.0, value))
      Color.YUV(clamp(y), clamp(u), clamp(v))
    }
    case Color.YUV(_, _, _) => color.asInstanceOf[Color.YUV]
  end toYUV

  def colorCloseness(color1: Color, color2: Color): Double =
    import Math._
    val Color.YUV(y1, u1, v1) = Color.toYUV(color1)
    val Color.YUV(y2, u2, v2) = Color.toYUV(color2)
    sqrt(
      pow(y2 - y1, 2) + 
      pow(u2 - u1, 2) + 
      pow(v2 - v1, 2)
    )
  end colorCloseness
end Color
