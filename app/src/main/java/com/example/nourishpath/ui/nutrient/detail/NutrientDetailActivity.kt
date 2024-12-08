package com.example.nourishpath.ui.nutrient.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nourishpath.R
import com.example.nourishpath.databinding.ActivityNutrientDetailBinding
import com.example.nourishpath.ui.nutrient.Food
import com.example.nourishpath.ui.nutrient.nutritionfacts.NutritionFactsActivity

class NutrientDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNutrientDetailBinding
    private lateinit var viewModel: NutrientDetailViewModel
    private lateinit var adapter: DetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutrientDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val selectedFoods = intent.getParcelableArrayListExtra<Food>("selectedFoods")
        val usia = intent.getIntExtra("usia", 0)

        Log.d("SelectedFoods of DetailActivity", "Daftar terpilih: ${selectedFoods?.map { it.category }} ${selectedFoods?.map { it.description }}")
        Log.d("DetailActivity", "SelectedFood Size: ${selectedFoods?.size}")
        Log.d("DetailActivity", "Usia: $usia")
        viewModel = ViewModelProvider(this)[NutrientDetailViewModel::class.java]

        if (selectedFoods != null) {
            viewModel.setSelectedFoods(selectedFoods)
            Log.d("DetailActivity", "Executed")
        }
        adapter = DetailAdapter { food, amount ->
            viewModel.updateAmount(food, amount)
        }
        binding.listFoodsItem.adapter = adapter
        binding.listFoodsItem.layoutManager = LinearLayoutManager(this)

        viewModel.selectedFoods.observe(this) { foods ->
            adapter.submitList(foods.toList())
        }
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        binding.btnSubmit.setOnClickListener {
            viewModel.selectedFoods.value?.forEach { food ->
                val amount = food.amount
                Log.d("FoodAmountCheck", "Food: ${food.description}, Amount: $amount, Food Amount: ${food.amount}")
            }
            viewModel.postNutrientData(usia)
        }

        viewModel.recommendations.observe(this) { recommendations ->
            if (recommendations != null) {
                val intent = Intent(this, NutritionFactsActivity::class.java).apply {
                    putExtra("RECOMMENDATIONS", recommendations)
                    flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Recommendations not available", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}