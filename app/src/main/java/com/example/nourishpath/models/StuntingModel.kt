package com.example.nourishpath.models

import android.content.Context
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class StuntingModel(private val context: Context) {
    private lateinit var interpreter: Interpreter

    // Load model TFLite dari assets
    fun loadModel(modelFileName: String) {
        val assetFileDescriptor = context.assets.openFd(modelFileName)
        val inputStream = assetFileDescriptor.createInputStream()
        val fileChannel = inputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        val mappedByteBuffer: MappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)

        interpreter = Interpreter(mappedByteBuffer)
    }

    // Fungsi untuk menjalankan model
    fun predictStunting(age: Float, weight: Float, height: Float): Float {
        // Pastikan input berbentuk [1, 5]
        val input = arrayOf(floatArrayOf(age, weight, height, 0.0f, 0.0f)) // Tambahkan dua nilai dummy (0.0f)

        val output = Array(1) { FloatArray(1) } // Output berbentuk [1, 1]

        interpreter.run(input, output)

        Log.d("Prediction", output[0][0].toString()) // Tambahkan ini untuk mengecek hasil
        Log.d("Prediction input", input[0].contentToString())
        return output[0][0]
    }


    // Hentikan interpreter
    fun close() {
        interpreter.close()
    }
}