package com.github.doandadr.petualanganhijaiyah.ml

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import org.tensorflow.SavedModelBundle
import org.tensorflow.ndarray.NdArrays
import org.tensorflow.ndarray.Shape
import org.tensorflow.types.TFloat32
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.max
import kotlin.math.sqrt


interface Prediction {
    fun predict(input: Array<FloatArray>): FloatArray
}

class PreProcessHelper {
    private lateinit var  segments: List<List<Vector2>>
    private lateinit var boardPosition: Vector2
    private var boardSize: Float = 0f

    companion object {
        private const val GRID_SIZE = 64
        private const val BRUSH_RADIUS = 4
        private const val MAX_GRAYSCALE_VALUE = 1f
    }

    // Initialize a 64x64 boolean array and a 64x64 float array
    private val booleanArray = Array(GRID_SIZE) { BooleanArray(GRID_SIZE) }
    private val grayscaleArray = Array(GRID_SIZE) { FloatArray(GRID_SIZE) }

    // Function to convert local coordinates to grid indices
    private fun convertToGridCoordinates(localX: Float, localY: Float, boardX: Float, boardY: Float): Vector2 {
        // Assuming the drawing board is aligned to the stage and 0, 0 is the bottom left corner
        val gridX = ((localX - boardX) / boardSize * GRID_SIZE).toInt()
        val gridY = ((localY - boardY) / boardSize * GRID_SIZE).toInt()
        return Vector2(gridX.toFloat(), (GRID_SIZE - gridY).toFloat())
    }

    // Function to populate the boolean array with segment locations and draw lines
    private fun populateBooleanArray() {
        for (segment in segments) {
            for (i in 0 until segment.size - 1) {
                val start = segment[i]
                val end = segment[i + 1]

                // Convert start and end points to grid coordinates
                val startGrid = convertToGridCoordinates(start.x, start.y, boardPosition.x, boardPosition.y)
                val endGrid = convertToGridCoordinates(end.x, end.y, boardPosition.x, boardPosition.y)

                // Draw line between startGrid and endGrid using Bresenham's algorithm
                drawLine(startGrid.x.toInt(), startGrid.y.toInt(), endGrid.x.toInt(), endGrid.y.toInt())
            }
        }
    }

    // Function to draw a line using Bresenham's algorithm
    private fun drawLine(x0: Int, y0: Int, x1: Int, y1: Int) {
        var x0 = x0
        var y0 = y0
        val dx = abs(x1 - x0)
        val dy = abs(y1 - y0)
        val sx = if (x0 < x1) 1 else -1
        val sy = if (y0 < y1) 1 else -1
        var err = dx - dy

        while (true) {
            if (x0 in 0 until GRID_SIZE && y0 in 0 until GRID_SIZE) {
                booleanArray[x0][y0] = true
            }
            if (x0 == x1 && y0 == y1) break
            val e2 = err * 2
            if (e2 > -dy) {
                err -= dy
                x0 += sx
            }
            if (e2 < dx) {
                err += dx
                y0 += sy
            }
        }
    }

    // Function to apply a circular brush effect
    private fun applyBrushEffect() {
        // Loop through the boolean array and apply the brush effect on the grayscale array
        for (x in 0 until GRID_SIZE) {
            for (y in 0 until GRID_SIZE) {
                if (booleanArray[x][y]) {
                    // For each point marked as true, apply the brush effect
                    for (dx in -BRUSH_RADIUS..BRUSH_RADIUS) {
                        for (dy in -BRUSH_RADIUS..BRUSH_RADIUS) {
                            val nx = x + dx
                            val ny = y + dy

                            // Check if the point is within the bounds of the grid
                            if (nx in 0 until GRID_SIZE && ny in 0 until GRID_SIZE) {
                                // Calculate the distance from the center (x, y) to (nx, ny)
                                val distance = sqrt((dx * dx + dy * dy).toDouble()).toFloat()

                                // If the distance is within the brush's radius, apply a diminishing value
                                if (distance <= BRUSH_RADIUS) {
                                    // Calculate the intensity based on the distance with Gaussian distribution
                                    val sigma = BRUSH_RADIUS / 2.0
                                    val intensity =
                                        (exp(-0.5 * (distance * distance) / (sigma * sigma)) * MathUtils.random(
                                            0.95f,
                                            1.05f
                                        )).toFloat()
                                    // Update the grayscale array with the maximum intensity
                                    grayscaleArray[nx][ny] = max(grayscaleArray[nx][ny], intensity)
                                    // Ensure the value does not exceed the maximum intensity
                                    if (grayscaleArray[nx][ny] > MAX_GRAYSCALE_VALUE) {
                                        grayscaleArray[nx][ny] = MAX_GRAYSCALE_VALUE
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Main function to process the drawing
    fun preProcessDrawing(
        segments: List<List<Vector2>>,
        boardPosition: Vector2,
        boardSize: Float
    ): Array<FloatArray> {
        this.boardSize = boardSize
        this.boardPosition = boardPosition
        this.segments = segments

        // Step 1: Populate the boolean array with segment locations
        populateBooleanArray()

        // Step 2: Apply the brush effect to the grayscale array
        applyBrushEffect()

        return grayscaleArray
    }
}

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

    fun predict(model: SavedModelBundle, input: Array<FloatArray>): FloatArray {
        // Create the NdArray with the desired shape (1, 64, 64, 1)
        val inputShape = Shape.of(1, 64, 64, 1)
        val inputBuffer = NdArrays.ofFloats(inputShape)

        // Fill the input tensor with the normalized pixel values from the 2D array
        for (row in 0 until 64) {
            for (col in 0 until 64) {
                inputBuffer.setFloat(input[row][col], 0, row.toLong(), col.toLong(), 0)
            }
        }
        val inputTensor = TFloat32.tensorOf(inputBuffer)

        // Predict
        val imagePredictions = run {
            val result: TFloat32 = model.session().runner()
                .feed("serving_default_conv2d_5_input:0", inputTensor)
                .fetch("StatefulPartitionedCall:0")
                .run().get(0) as TFloat32
            result.copyTo(NdArrays.ofFloats(result.shape()))
        }
        val predictionSize = imagePredictions.shape().size(1).toInt()

        val predictions = FloatArray(predictionSize)
        // Fill the predictions array with the output values
        for (i in 0 until predictionSize) {
            predictions[i] = imagePredictions.getFloat(0, i.toLong())
        }

        for (i in 0 until predictionSize) {
            print("${"%2d".format(i)}   ")
        }
        println()
        for (j in 0 until imagePredictions.shape().size(1)) {
            val value = imagePredictions.getFloat(0, j)
            print("${"%.2f".format(value)} ")
        }
        println()

        return predictions
    }

    fun sortPredictions(predictions: FloatArray): List<Pair<Int, Float>> {
        return predictions.mapIndexed { index, value -> index to value }.sortedByDescending { it.second }
    }
}
