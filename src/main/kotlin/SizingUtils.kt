import java.awt.image.BufferedImage

fun BufferedImage.hasSameDimensionsAs(other: BufferedImage) =
    this.width == other.width && this.height == other.height

fun BufferedImage.hasSmallerOrSameDimensionsAs(other: BufferedImage) =
    this.width >= other.width && this.height >= other.height
