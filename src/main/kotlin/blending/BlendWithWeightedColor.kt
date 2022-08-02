package blending

import color.blend
import java.awt.Color

class BlendWithWeightedColor(private val weight: Int, useWatermarkAlpha: Boolean = false) :
    BlendingStrategy(useWatermarkAlpha) {
    override fun blend(imageColor: Color, watermarkColor: Color) =
        if (watermarkColor.alpha != 255) imageColor else imageColor.blend(watermarkColor, weight)
}
