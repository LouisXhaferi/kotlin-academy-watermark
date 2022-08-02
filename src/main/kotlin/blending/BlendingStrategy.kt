package blending

import java.awt.Color

abstract class BlendingStrategy(val useWatermarkAlpha: Boolean = false) {
    abstract fun blend(imageColor: Color, watermarkColor: Color): Color
}
