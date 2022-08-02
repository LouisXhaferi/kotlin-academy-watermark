package io

import Constants.TITLE_IMAGE
import Constants.TITLE_WATERMARK
import Constants.WHITESPACE
import Positioning
import color.RGB
import hasSameDimensionsAs
import java.awt.image.BufferedImage

data class Inputs(
    val image: BufferedImage,
    val watermark: BufferedImage,
    val weight: Int,
    val outputFilename: String,
    val useWatermarkAlpha: Boolean,
    val useTransparentColors: Boolean,
    val transparentColors: RGB = RGB(0, 0, 0),
    val positioning: Positioning
) {
    companion object {
        fun readOrNull(): Inputs? {
            println("Input the image filename:")
            val image = getImage(readln(), TITLE_IMAGE) ?: return null

            println("Input the watermark image filename:")
            val watermark = getImage(readln(), TITLE_WATERMARK) ?: return null

            if (watermark.width > image.width || watermark.height > image.height) {
                println("The watermark's dimensions are larger.")
                return null
            }

            var useAlpha = false
            var useTransparentColors = false
            if (watermark.transparency == 3) {
                println("Do you want to use the watermark's Alpha channel?")
                useAlpha = readln().lowercase() == "yes"
            } else {
                println("Do you want to set a transparency color?")
                useTransparentColors = readln().lowercase() == "yes"
            }

            var transparentColors = RGB(0, 0, 0)
            if (useTransparentColors) {
                println("Input a transparency color ([Red] [Green] [Blue]):")
                try {
                    val colors = readln().split(WHITESPACE).map { it.toInt() }.filter { it in 0..255 }

                    if (colors.size != 3) {
                        throw Exception("Invalid input")
                    }

                    val (red, green, blue) = colors
                    transparentColors = RGB(red, green, blue)
                } catch (e: Exception) {
                    println("The transparency color input is invalid.")
                    return null
                }
            }

            println("Input the watermark transparency percentage (Integer 0-100):")
            val transparency = getTransparency(readln()) ?: return null

            val positioning = if (image.hasSameDimensionsAs(watermark)) {
                Positioning.cover()
            } else {
                println("Choose the position method (single, grid):")

                when (readln().lowercase()) {
                    "grid" -> Positioning.grid()
                    "single" -> {
                        val diffX = image.width - watermark.width
                        val diffY = image.height - watermark.height
                        println("Input the watermark position ([x 0-$diffX] [y 0-$diffY]): ")
                        try {
                            val (x, y) = readln().split(WHITESPACE).map { it.toInt() }

                            if (x > diffX || y > diffY || x < 0 || y < 0) {
                                println("The position input is out of range.")
                                return null
                            } else {
                                Positioning.coordinate(x, y)
                            }
                        } catch (e: Exception) {
                            println("The position input is invalid.")
                            return null
                        }
                    }

                    else -> {
                        println("The position method input is invalid.")
                        return null
                    }
                }
            }

            println("Input the output image filename (jpg or png extension):")
            val output = readln()
            if (!isValidOutputFilename(output)) {
                println("""The output file extension isn't "jpg" or "png".""")
                return null
            }

            return Inputs(
                image,
                watermark,
                transparency,
                output,
                useAlpha,
                useTransparentColors,
                transparentColors,
                positioning
            )
        }
    }
}

