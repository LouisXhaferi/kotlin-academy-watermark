import blending.BlendingStrategy
import java.awt.Color
import java.awt.image.BufferedImage

fun BufferedImage.watermark(
    image: BufferedImage,
    strategy: BlendingStrategy,
    positioning: Positioning
) = when (positioning) {
    is CoverPositioning -> watermarkCovering(image, strategy)
    is CoordinatePositioning -> watermarkAtCoordinate(image, strategy, positioning.x, positioning.y)
    is GridPositioning -> watermarkGrid(image, strategy)
    else -> throw NotImplementedError("Blending has not been implemented for this positioning yet.")
}

private fun BufferedImage.watermarkCovering(
    image: BufferedImage,
    strategy: BlendingStrategy
) {
    require(hasSameDimensionsAs(image)) {
        "Image and Watermark need to have same dimensions to apply cover positioning."
    }

    for (x in 0 until width) {
        for (y in 0 until height) {
            val color = Color(getRGB(x, y))
            val wColor = Color(image.getRGB(x, y), strategy.useWatermarkAlpha)

            val blended = strategy.blend(color, wColor)

            setRGB(x, y, blended.rgb)
        }
    }
}

private fun BufferedImage.watermarkAtCoordinate(
    image: BufferedImage,
    strategy: BlendingStrategy,
    posX: Int = 0,
    posY: Int = 0
) {
    require(hasSmallerOrSameDimensionsAs(image)) {
        "Watermark must fit into image to apply coordinate positioning."
    }

    for (x in 0 until width) {
        for (y in 0 until height) {
            if (!(x in posX until (posX + image.width) && y in posY until (posY + image.height))) {
                continue
            }

            val color = Color(getRGB(x, y))
            val wColor = Color(image.getRGB(x - posX, y - posY), strategy.useWatermarkAlpha)

            val blended = strategy.blend(color, wColor)
            setRGB(x, y, blended.rgb)
        }
    }

}

private fun BufferedImage.watermarkGrid(image: BufferedImage, strategy: BlendingStrategy) {
    require(hasSmallerOrSameDimensionsAs(image)) {
        "Watermark must fit into image to apply grid positioning."
    }

    for (x in 0 until width) {
        for (y in 0 until height) {
            val color = Color(getRGB(x, y))
            val wColor = Color(image.getRGB(x % image.width, y % image.height), strategy.useWatermarkAlpha)

            val blended = strategy.blend(color, wColor)

            setRGB(x, y, blended.rgb)
        }
    }
}

