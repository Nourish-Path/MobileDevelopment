package com.example.nourishpath.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.example.nourishpath.FoodSearchAdapter
import com.example.nourishpath.R
import com.example.nourishpath.databinding.FragmentNutrientBinding
import com.example.nourishpath.models.NutrientData
import com.example.nourishpath.ui.FoodListAdapter
import com.opencsv.CSVReader
import java.io.InputStreamReader

class NutrientFragment : Fragment() {
    private var _binding: FragmentNutrientBinding? = null
    private val binding get() = _binding!!
    private var age: Float? = null
    private var weight: Float? = null
    private var height: Float? = null
    private val foodDataMap = mutableMapOf<String, Map<String, Float>>() // Data nutrisi makanan
    private val selectedFoodList = mutableListOf<String>() // Makanan yang dipilih

    private lateinit var selectedAdapter: FoodListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNutrientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Terima data dari FragmentChildInput
        arguments?.let {
            age = it.getFloat("age")
            weight = it.getFloat("weight")
            height = it.getFloat("height")
        }

        if (age == null || weight == null || height == null) {
            Toast.makeText(requireContext(), "Data anak tidak ditemukan!", Toast.LENGTH_SHORT)
                .show()
            findNavController().popBackStack()
            return
        }

        Log.d("ChildData", "Age: $age, Weight: $weight, Height: $height")


        // Load food data dari assets
        loadFoodData()

        // Adapter untuk daftar makanan yang dipilih
        selectedAdapter = FoodListAdapter(selectedFoodList)
        binding.rvSelectedFood.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSelectedFood.adapter = selectedAdapter

        // Adapter untuk hasil pencarian
        val searchAdapter = FoodSearchAdapter(mutableListOf()) { selectedFood ->
            if (!selectedFoodList.contains(selectedFood)) {
                selectedFoodList.add(selectedFood)
                selectedAdapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "$selectedFood ditambahkan!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "$selectedFood sudah ada di daftar!", Toast.LENGTH_SHORT).show()
            }
        }

        // RecyclerView untuk hasil pencarian
        binding.rvFoodList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFoodList.adapter = searchAdapter

        // Listener untuk pencarian makanan
        binding.etFoodInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                val filteredFoods = foodDataMap.keys.filter { it.contains(query, ignoreCase = true) }
                searchAdapter.updateData(filteredFoods)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Tombol untuk menghitung nutrisi total
        binding.btnCalculate.setOnClickListener {
            if (selectedFoodList.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Daftar makanan kosong. Tambahkan makanan terlebih dahulu!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val totalNutrients = calculateTotalNutrients()

            val nutrientData = NutrientData(LinkedHashMap(totalNutrients))

            val bundle = Bundle().apply {
                putParcelable("nutrientData", nutrientData)
            }

            findNavController().navigate(R.id.fragment_nutrient_result, bundle)
        }
    }

    private fun loadFoodData() {
        val inputStream = requireContext().assets.open("food_data.csv")
        val reader = CSVReader(InputStreamReader(inputStream))

        val lines = reader.readAll()
        val dataRows = lines.drop(1) // Abaikan header

        dataRows.forEach { line ->
            try {
                if (line.isNotEmpty() && line.size >= 5) {
                    val foodName = line[1].trim()

                    val nutrients = linkedMapOf(
                        "Carbohydrates" to (line[7].toFloatOrNull() ?: 0f),
                        "Protein" to (line[12].toFloatOrNull() ?: 0f),
                        "Fat" to (line[21].toFloatOrNull() ?: 0f),
                        "Calories" to (line[6].toFloatOrNull() ?: 0f) * 4
                    )

                    foodDataMap[foodName.lowercase()] = nutrients

                }
            } catch (e: Exception) {
                Log.e("FoodDataLoader", "Error processing line: ${line.joinToString(", ")}", e)
            }
        }
        reader.close()

        Log.d("FoodDataLoader", "Loaded data: ${foodDataMap.size} items")
    }

    private fun calculateTotalNutrients(): LinkedHashMap<String, Float> {
        val totalNutrients = LinkedHashMap<String, Float>().apply {
            put("Calories", 0f)
            put("Protein", 0f)
            put("Fat", 0f)
            put("Carbohydrates", 0f)
        }

        for (food in selectedFoodList) {
            val nutrients = foodDataMap[food] ?: continue
            nutrients.forEach { (key, value) ->
                totalNutrients[key] = totalNutrients.getOrDefault(key, 0f) + value
            }
        }
        return totalNutrients
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
