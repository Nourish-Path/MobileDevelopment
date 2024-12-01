package com.example.nourishpath.ui.nutrient

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nourishpath.databinding.ActivityChildInputBinding
import com.example.nourishpath.ui.nutrient.models.StuntingModel

class ChildInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChildInputBinding
    private lateinit var stuntingModel: StuntingModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        stuntingModel = StuntingModel(this)
        stuntingModel.loadModel("checkstunting.tflite")

        binding.btnNext.setOnClickListener {
            val age = binding.etChildAge.text.toString().toFloatOrNull()
            val weight = binding.etChildWeight.text.toString().toFloatOrNull()
            val height = binding.etChildHeight.text.toString().toFloatOrNull()

            if (age == null || weight == null || height == null) {
                Toast.makeText(this, "Harap isi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prediction = stuntingModel.predictStunting(age, weight, height)
            val isStunting = prediction >= 0.5
            val message = if (isStunting) {
                "Anak berpotensi stunting!"
            } else {
                "Anak tidak stunting."
            }

            Toast.makeText(this, message, Toast.LENGTH_LONG).show()

            val intent = Intent(this, NutrientActivity::class.java).apply {
                putExtra("age", age)
                putExtra("weight", weight)
                putExtra("height", height)
            }
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stuntingModel.close()
    }
}
