package io

import Constants.BITS_24
import Constants.BITS_32
import Constants.EXT_JPG
import Constants.EXT_PNG
import java.awt.image.BufferedImage
import java.awt.image.ColorModel
import java.io.File
import javax.imageio.ImageIO

fun getImage(filename: String, title: String): BufferedImage? {
    val file = getFile(filename) ?: return null

    val image = ImageIO.read(file)
    val color = image.colorModel
    if (!hasThreeComponentsElseLog(color, title)) return null
    if (!hasOneOfPixelSizesOrLog(color, title, BITS_24, BITS_32)) return null

    return image
}

private fun getFile(filename: String): File? {
    val file = File(filename)
    if (!file.exists() || file.isDirectory) {
        println("The file $filename doesn't exist.")
        return null
    }
    return file
}

fun getTransparency(input: String): Int? {
    try {
        val num = input.toInt()

        if (isInvalidTransparency(num)) {
            println("The transparency percentage is out of range.")
            return null
        }
        return num
    } catch (e: NumberFormatException) {
        println("The transparency percentage isn't an integer number.")
        return null
    }
}

fun saveNewImage(image: BufferedImage, filename: String) {
    val file = File(filename)
    ImageIO.write(image, filename.split(".")[1], file)
    println("The watermarked image $filename has been created.")
}

private fun hasOneOfPixelSizes(color: ColorModel, vararg sizes: Int) = sizes.contains(color.pixelSize)

private fun hasOneOfPixelSizesOrLog(color: ColorModel, ofWhat: String, vararg sizes: Int): Boolean {
    if (!(hasOneOfPixelSizes(color, *sizes))) {
        println("The $ofWhat isn't 24 or 32-bit.")
        return false
    }

    return true
}

private fun hasThreeComponentsElseLog(color: ColorModel, ofWhat: String): Boolean {
    if (color.numColorComponents != 3) {
        println("The number of $ofWhat color components isn't 3.")
        return false
    }

    return true
}

fun isValidOutputFilename(name: String) = name.endsWith(EXT_PNG) || name.endsWith(EXT_JPG)

private fun isInvalidTransparency(transparency: Int) = !isValidTransparency(transparency)
private fun isValidTransparency(transparency: Int) = transparency in 0..100
