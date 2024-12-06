package com.example.nourishpath.ui.nutrient.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nourishpath.R
import com.example.nourishpath.databinding.ActivityNutrientDetailBinding
import com.example.nourishpath.ui.nutrient.Food

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
            adapter.submitList(foods)
        }
    }
}