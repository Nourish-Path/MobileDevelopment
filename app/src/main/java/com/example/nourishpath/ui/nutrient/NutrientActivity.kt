//package com.example.nourishpath.ui.nutrient
//
//import android.content.Intent
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.nourishpath.databinding.ActivityNutrientBinding
//import com.example.nourishpath.ui.nutrient.adapter.FoodListAdapter
//import com.example.nourishpath.ui.nutrient.adapter.FoodSearchAdapter
//import com.example.nourishpath.ui.nutrient.models.NutrientData
//import com.opencsv.CSVReader
//import java.io.InputStreamReader
//
//class NutrientActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityNutrientBinding
//    private var age: Float? = null
//    private var weight: Float? = null
//    private var height: Float? = null
//    private val foodDataMap = mutableMapOf<String, Map<String, Float>>()
//    private val selectedFoodList = mutableListOf<String>()
//    private lateinit var selectedAdapter: FoodListAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityNutrientBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        age = intent.getFloatExtra("age", 0f)
//        weight = intent.getFloatExtra("weight", 0f)
//        height = intent.getFloatExtra("height", 0f)
//
//        if (age == null || weight == null || height == null) {
//            Toast.makeText(this, "Data anak tidak ditemukan!", Toast.LENGTH_SHORT).show()
//            finish()
//            return
//        }
//
//        Log.d("ChildData", "Age: $age, Weight: $weight, Height: $height")
//
//        loadFoodData()
//
//        selectedAdapter = FoodListAdapter(selectedFoodList)
//        binding.rvSelectedFood.layoutManager = LinearLayoutManager(this)
//        binding.rvSelectedFood.adapter = selectedAdapter
//
//        val searchAdapter = FoodSearchAdapter(mutableListOf()) { selectedFood ->
//            if (!selectedFoodList.contains(selectedFood)) {
//                selectedFoodList.add(selectedFood)
//                selectedAdapter.notifyDataSetChanged()
//                Toast.makeText(this, "$selectedFood ditambahkan!", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "$selectedFood sudah ada di daftar!", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//
//        binding.rvFoodList.layoutManager = LinearLayoutManager(this)
//        binding.rvFoodList.adapter = searchAdapter
//
//        binding.etFoodInput.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val query = s.toString().trim()
//                val filteredFoods =
//                    foodDataMap.keys.filter { it.contains(query, ignoreCase = true) }
//                searchAdapter.updateData(filteredFoods)
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })
//
//        binding.btnCalculate.setOnClickListener {
//            if (selectedFoodList.isEmpty()) {
//                Toast.makeText(
//                    this,
//                    "Daftar makanan kosong. Tambahkan makanan terlebih dahulu!",
//                    Toast.LENGTH_SHORT
//                ).show()
//                return@setOnClickListener
//            }
//
//            val totalNutrients = calculateTotalNutrients()
//
//            val nutrientData =
//                NutrientData(HashMap(totalNutrients))
//
//            val intent = Intent(this, NutrientResultActivity::class.java).apply {
//                putExtra("nutrientData", nutrientData)
//            }
//            startActivity(intent)
//        }
//    }
//
//    private fun loadFoodData() {
//        val inputStream = assets.open("food_data.csv")
//        val reader = CSVReader(InputStreamReader(inputStream))
//
//        val lines = reader.readAll()
//        val header = lines.first()
//        val dataRows = lines.drop(1)
//
//        dataRows.forEach { line ->
//            try {
//                if (line.isNotEmpty() && line.size >= header.size) {
//                    val foodName = line[1].trim()
//
//                    val nutrients = hashMapOf<String, Float>()
//                    for (i in 2 until header.size) {
//                        val nutrientName = header[i].trim()
//                        val nutrientValue = line[i].toFloatOrNull() ?: 0f
//                        nutrients[nutrientName] = nutrientValue
//                    }
//
//                    foodDataMap[foodName.lowercase()] = nutrients
//                }
//            } catch (e: Exception) {
//                Log.e("FoodDataLoader", "Error processing line: ${line.joinToString(", ")}", e)
//            }
//        }
//        reader.close()
//
//        Log.d("FoodDataLoader", "Loaded data: ${foodDataMap.size} items")
//    }
//
//    private fun calculateTotalNutrients(): HashMap<String, Double> {
//        val totalNutrients = HashMap<String, Double>()
//
//        for (food in selectedFoodList) {
//            val nutrients = foodDataMap[food] ?: continue
//            nutrients.forEach { (key, value) ->
//                totalNutrients[key] = totalNutrients.getOrDefault(key, 0.0) + value.toDouble()
//            }
//        }
//
//        return totalNutrients
//    }
//}