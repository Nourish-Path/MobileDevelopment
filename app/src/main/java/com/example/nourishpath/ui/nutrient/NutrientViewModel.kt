package com.example.nourishpath.ui.nutrient

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.opencsv.CSVReader
import kotlinx.coroutines.launch
import java.io.InputStreamReader

class NutrientViewModel(application: Application): AndroidViewModel(application) {
    private val _foodList = MutableLiveData<List<Food>>()
    val foodList: LiveData<List<Food>> = _foodList

    private val _selectedFoods = MutableLiveData<List<Food>>()
    val selectedFoods: LiveData<List<Food>> = _selectedFoods

    init {
        loadFoodData()
    }

    fun loadFoodData() {
        viewModelScope.launch {
            try {
                val inputStream = getApplication<Application>().assets.open("food_data.csv")
                val reader = CSVReader(InputStreamReader(inputStream))
                val lines = reader.readAll()
                val dataRows = lines.drop(1)

                val foodList = mutableListOf<Food>()

                dataRows.forEach { line ->
                    if (line.isNotEmpty() && line.size >= 2) {
                        val category = line[0].trim()
                        val description = line[1].trim()
                        val isSelected = _selectedFoods.value?.any { it.description == description } == true
                        foodList.add(Food(category, description, isSelected = isSelected))
                    }
                }
                _foodList.postValue(foodList)
            } catch (e: Exception) {
                Log.e("NutrientViewModel", "Error loading data", e)
            }
        }
    }
    fun filterFoodList(query: String) {
        val filteredList = _foodList.value?.map { food ->
            food.copy(isSelected = _selectedFoods.value?.any { it.description == food.description } == true)
        }?.filter {
            it.description.contains(query, ignoreCase = true)
        } ?: emptyList()
        _foodList.postValue(filteredList)
    }

    fun addSelectedFood(food: Food) {
        val currentList = _selectedFoods.value?.toMutableList() ?: mutableListOf()

        if (!currentList.any { it.description == food.description }) {
            currentList.add(food)
            _selectedFoods.postValue(currentList)

            toggleSelection(food, true)
        }
    }

    fun removeSelection(food: Food) {
        val currentList = _selectedFoods.value?.toMutableList() ?: mutableListOf()

        // Jika item ada dalam daftar, hapus
        if (currentList.any { it.description == food.description }) {
            currentList.removeAll { it.description == food.description }
            _selectedFoods.value = currentList

            // Update isSelected menjadi false pada foodList
            toggleSelection(food, false)
        }
    }

    private fun toggleSelection(food: Food, isSelected: Boolean) {
        val currentList = _foodList.value?.toMutableList() ?: mutableListOf()
        val index = currentList.indexOfFirst { it.description == food.description }

        if (index != -1) {
            val updatedFood = currentList[index].copy(isSelected = isSelected)
            currentList[index] = updatedFood
            _foodList.value = currentList
        }
    }
}