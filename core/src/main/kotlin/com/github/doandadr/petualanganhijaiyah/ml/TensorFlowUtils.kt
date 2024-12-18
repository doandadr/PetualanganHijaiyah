package com.github.doandadr.petualanganhijaiyah.ml

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object TensorFlowUtils {
    fun normalizeAndReshape(pixelArray: IntArray, height: Int = 64, width: Int = 64): Array<FloatArray> {
        val totalSize = height * width
        require(pixelArray.size == totalSize) { "The size of the input array does not match the specified dimensions." }

        // Normalize the pixel values (0-255 to 0-1) and reshape to a 2D array
        val reshapedArray = Array(height) { FloatArray(width) }
        for (i in pixelArray.indices) {
            val row = i / width
            val col = i % width
            reshapedArray[row][col] = pixelArray[i] / 255.0f
        }

        return reshapedArray
    }

    fun normalize(input: IntArray): FloatArray {
        val normalizedArray = FloatArray(4096) { i -> input[i] / 255.0f }
        return normalizedArray
    }

    fun convertValuesToImage(imageValues: FloatArray, display: Boolean = false): BufferedImage {
        // Reshape and normalize the image values
        val width = 64
        val height = 64
        val imageArray = Array(height) { FloatArray(width) }
        for (i in imageValues.indices) {
            val row = i / width
            val col = i % width
            imageArray[row][col] = imageValues[i]
        }

        // Convert to BufferedImage
        val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
        for (y in 0 until height) {
            for (x in 0 until width) {
                val value = (imageArray[y][x] * 255).toInt()
                val rgb = (value shl 16) or (value shl 8) or value
                bufferedImage.setRGB(x, y, rgb)
            }
        }

        // Optionally display the image
        if (display) {
            val file = File("image.png")
            ImageIO.write(bufferedImage, "png", file)
            println("Image saved to ${file.absolutePath}")
        }

        return bufferedImage
    }

    fun sortPredictions(predictions: FloatArray): List<Pair<Int, Float>> {
        return predictions.mapIndexed { index, value -> index to value }.sortedByDescending { it.second }
    }
}
