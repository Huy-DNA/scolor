package scolor.color

import scala.util.matching.Regex

enum Color:
  // r, g, b must be in [0, 1]
  case RGB(r: Double, g: Double, b: Double)
  // h, s, l must be in [0, 1]
  case HSL(h: Double, s: Double, l: Double)

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
  end toHSL

  def colorCloseness(color1: Color, color2: Color): Double =
    import Math._
    val Color.HSL(h1, s1, l1) = Color.toHSL(color1)
    val Color.HSL(h2, s2, l2) = Color.toHSL(color2)

    // Normalize hue difference
    val dH = min(abs(h2 - h1), 1.0 - abs(h2 - h1))
    
    // Saturation and lightness differences
    val dS = abs(s2 - s1)
    val dL = abs(l2 - l1)
    
    // Perceptual weights
    val W_H = 1.0
    val W_S = 0.5
    val W_L = 0.2
    
    // Maximum perceptual distance
    val maxDistance = sqrt(pow(W_H * 1.0, 2) + pow(W_S * 1.0, 2) + pow(W_L * 1.0, 2))
    
    // Perceptual distance
    val distance = sqrt(pow(W_H * dH, 2) + pow(W_S * dS, 2) + pow(W_L * dL, 2))
    
    // Closeness as a percentage (1 = identical, 0 = max distance)
    (1.0 - (distance / maxDistance))
  end colorCloseness
end Color
