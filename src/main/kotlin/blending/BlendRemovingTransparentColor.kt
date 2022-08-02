package blending

import color.RGB
import color.blend
import java.awt.Color

private const val NO_ALPHA = false

class BlendRemovingTransparentColor(
    private val color: RGB,
    private val weight: Int
) :
    BlendingStrategy(NO_ALPHA) {
    override fun blend(imageColor: Color, watermarkColor: Color) =
        if (color.same(watermarkColor)) imageColor else imageColor.blend(watermarkColor, weight)
}
