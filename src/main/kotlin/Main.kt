import blending.BlendRemovingTransparentColor
import blending.BlendWithWeightedColor
import blending.BlendingStrategy
import io.Inputs
import io.saveNewImage

fun main() {
    Inputs.readOrNull()?.run {
        image.watermark(watermark, determineAppropriateStrategy(useWatermarkAlpha), positioning)
        saveNewImage(image, outputFilename)
    }
}

fun Inputs.determineAppropriateStrategy(useWatermarkAlpha: Boolean): BlendingStrategy {
    if (useTransparentColors) {
        return BlendRemovingTransparentColor(transparentColors, weight)
    }

    return BlendWithWeightedColor(weight, useWatermarkAlpha)
}
