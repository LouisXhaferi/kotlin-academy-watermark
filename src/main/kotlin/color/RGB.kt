package color

import java.awt.Color

data class RGB(val red: Int, val green: Int, val blue: Int) {
    fun same(color: Color) = this.red == color.red && this.green == color.green && this.blue == color.blue
}

fun Color.blend(other: Color, weight: Int): Color = Color(
    (weight * other.red + (100 - weight) * red) / 100,
    (weight * other.green + (100 - weight) * green) / 100,
    (weight * other.blue + (100 - weight) * blue) / 100
)
