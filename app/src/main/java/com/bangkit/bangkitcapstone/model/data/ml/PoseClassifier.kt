package com.bangkit.bangkitcapstone.model.data.ml

import android.content.res.AssetManager
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class PoseClassifier(assetManager: AssetManager, modelPath: String) {
    private var interpreter: Interpreter

    // Init tflite interpreter
    init {
        val options = Interpreter.Options()
        options.setNumThreads(5)
        options.setUseNNAPI(true)
        interpreter = Interpreter(loadModelFile(assetManager, modelPath), options)
        //lableList = loadLabelList(assetManager, labelPath)
    }

    // Load model from app/src/main/assets folder
    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    // Predicts pose from input array and returns a float number
    fun predictPose(keypointsArray: List<Float>): Float {
        val input = FloatArray(keypointsArray.size)
        for (i in keypointsArray.indices) {
            input[i] = keypointsArray[i]
        }
        val result = Array(1) { FloatArray(1) }
        interpreter.run(input, result)
        return result[0][0]
    }

}