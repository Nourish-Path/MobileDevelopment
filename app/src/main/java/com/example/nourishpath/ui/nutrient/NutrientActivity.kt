package com.example.nourishpath.ui.nutrient

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nourishpath.databinding.ActivityNutrientBinding
import com.example.nourishpath.ui.nutrient.detail.NutrientDetailActivity

class NutrientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNutrientBinding
    private val viewModel by viewModels<NutrientViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutrientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = FoodAdapter()

        val usia = intent.getIntExtra("usia", 0)

        Log.d("Usia", "Usia: $usia")
        val layoutManager = LinearLayoutManager(this@NutrientActivity)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : FoodAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Food) {
                viewModel.addSelectedFood(data) // Tambahkan ke ViewModel
                Toast.makeText(this@NutrientActivity, "${data.description} added", Toast.LENGTH_SHORT).show()
            }
        })

        adapter.setOnItemRemoveCallback(object : FoodAdapter.OnItemRemoveCallback {
            override fun onItemRemoved(data: Food) {
                viewModel.removeSelection(data)
                Toast.makeText(this@NutrientActivity, "${data.description} deleted from choice", Toast.LENGTH_SHORT).show()
            }
        })

        val editTextSearch: EditText = binding.editTextSearch

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                charSequence?.let { viewModel.filterFoodList(it.toString()) }
            }

            override fun afterTextChanged(editable: Editable?) {
                if (editable.isNullOrEmpty()) {
                    viewModel.loadFoodData()
                }
            }
        })

        viewModel.foodList.observe(this) { foodList ->
            adapter.submitList(foodList)
        }

        viewModel.selectedFoods.observe(this) { selectedFoods ->
            Log.d("SelectedFoods", "Daftar terpilih: ${selectedFoods.map { it.category }} ${selectedFoods.map { it.description }}")
        }

        binding.floatingBtn.setOnClickListener {
            val selectedFoods = viewModel.selectedFoods.value
            Log.d("SelectedFoods", "Daftar terpilih sebelum dikirim: ${selectedFoods?.map { it.isSelected }}")

            if (selectedFoods.isNullOrEmpty()) {
                Toast.makeText(this, "Pilih setidaknya satu makanan", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, NutrientDetailActivity::class.java)
                intent.putParcelableArrayListExtra("selectedFoods", ArrayList(selectedFoods)) // Kirim list Food
                intent.putExtra("usia", usia)
                startActivity(intent)
            }
        }
    }
}