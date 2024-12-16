package com.github.doandadr.petualanganhijaiyah.ml

import org.tensorflow.SavedModelBundle
import org.tensorflow.ndarray.NdArrays
import org.tensorflow.ndarray.Shape
import org.tensorflow.types.TFloat32


class TensorFlowModel(private val model: SavedModelBundle) {
    fun predict(input: Array<FloatArray>): Int {
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

        val imagePredictions = model.session().use { session ->
            val result: TFloat32 = session.runner()
                .feed("serving_default_conv2d_5_input:0", inputTensor)
                .fetch("StatefulPartitionedCall:0")
                .run().get(0) as TFloat32
            println("Output Tensor: $result")
            result.copyTo(NdArrays.ofFloats(result.shape()))
        }

        var maxIndex = 0
        var maxValue = -Float.MAX_VALUE

        for (j in 0 until imagePredictions.shape().size(1)) {
            val value = imagePredictions.getFloat(0, j)
            if (value > maxValue) {
                maxValue = value
                maxIndex = j.toInt()
            }
        }

        return maxIndex
    }

    fun normalizeAndReshape(pixelArray: IntArray, height: Int, width: Int): Array<FloatArray> {
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
}

//            Tensor.of(FloatNdArray::class.java, inputBuffer).use { inputTensor ->
//                session.runner()
//                    .feed("input_tensor_name", inputTensor) // Change to your input tensor name
//                    .fetch("output_tensor_name") // Change to your output tensor name
//                    .run().first().use { outputTensor ->
//                        val output = NdArrays.ofFloats(longArrayOf(1, 38)) // Change 38 to the number of classes in your model
//                        outputTensor.copyTo(output)
//                        return output.toFloatArray().map { it.toInt() }.toIntArray()
//                    }
//            }

//class TensorFlowModel(modelDir: String) {
//    private val model: SavedModelBundle = SavedModelBundle.load(modelDir, "serve")
//
//    fun predict(input: FloatArray): FloatArray {
//        model.session().use { session ->
//            Tensor.create(longArrayOf(1, 64, 64, 1), FloatBuffer.wrap(input)).use { inputTensor ->
//                session.runner()
//                    .feed("input_tensor_name", inputTensor) // Change to your input tensor name
//                    .fetch("output_tensor_name") // Change to your output tensor name
//                    .run().first().expect(Float::class.java).use { outputTensor ->
//                        val output = Array(1) { FloatArray(38) } // Change 38 to the number of classes in your model
//                        outputTensor.copyTo(output)
//                        return output[0]
//                    }
//            }
//        }
//    }
//}
