package com.github.doandadr.petualanganhijaiyah.ml
//
//import org.tensorflow.SavedModelBundle
//import org.tensorflow.Tensor
//import java.awt.image.BufferedImage
//import java.awt.Graphics2D
//import java.io.File
//import javax.imageio.ImageIO
//
//class TensorFlowModel(modelPath: String) {
//    private val model: SavedModelBundle = SavedModelBundle.load(modelPath, "serve")
//    fun predict(image: BufferedImage): String {
//        // Preprocess image
//        val input = preprocessImage(image)
//
//        // Create Tensor from input
//        val inputTensor = Tensor.create(input, Float::class.java)
//
//        // Run the model and get result
//        val result = model.session().runner()
//            .feed("input_tensor", inputTensor)
//            .fetch("output_tensor")
//            .run()[0]
//
//        // Extract prediction
//        val predictions = FloatArray(28)
//        result.copyTo(predictions)
//
//        // Find the index with the highest probability
//        val predictedClass = predictions.indices.maxByOrNull { predictions[it] } ?: -1
//
//        // Map the index to the corresponding Arabic character
//        val arabicCharacters = arrayOf("ا", "ب", "ت", /* Fill in all 28 Arabic characters */)
//        return arabicCharacters[predictedClass]
//    }
//
//    private fun preprocessImage(image: BufferedImage): Array<Array<Array<FloatArray>>> {
//        val width = 100
//        val height = 100
//        val resizedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
//        val g: Graphics2D = resizedImage.createGraphics()
//        g.drawImage(image, 0, 0, width, height, null)
//        g.dispose()
//
//        val input = Array(1) { Array(100) { Array(100) { FloatArray(3) } } }
//        for (i in 0 until 100) {
//            for (j in 0 until 100) {
//                val rgb = resizedImage.getRGB(j, i)
//                input[0][i][j][0] = ((rgb shr 16) and 0xFF) / 255.0f  // Red channel
//                input[0][i][j][1] = ((rgb shr 8) and 0xFF) / 255.0f   // Green channel
//                input[0][i][j][2] = (rgb and 0xFF) / 255.0f           // Blue channel
//            }
//        }
//        return input
//    }
//}
