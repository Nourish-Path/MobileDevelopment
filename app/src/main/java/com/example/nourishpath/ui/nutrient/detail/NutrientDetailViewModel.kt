package com.example.nourishpath.ui.nutrient.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nourishpath.data.api.nourishpath.ApiConfig
import com.example.nourishpath.data.api.nourishpath.request.FoodsItem
import com.example.nourishpath.data.api.nourishpath.request.NutrientRequest
import com.example.nourishpath.data.api.nourishpath.response.RecommendationResponse
import com.example.nourishpath.ui.nutrient.Food
import kotlinx.coroutines.launch

class NutrientDetailViewModel : ViewModel() {
    private val _selectedFoods = MutableLiveData<List<Food>>()
    val selectedFoods: LiveData<List<Food>> = _selectedFoods

    private val _recommendations = MutableLiveData<RecommendationResponse>()
    val recommendations: LiveData<RecommendationResponse> get() = _recommendations

    fun setSelectedFoods(foods: List<Food>) {
        _selectedFoods.value = foods
    }

    fun updateAmount(food: Food, newAmount: Float) {
        val updatedList = _selectedFoods.value?.map {
            if (it.description == food.description && it.category == food.category) {
                it.copy(amount = newAmount) // Perbarui amount
            } else {
                it
            }
        }
        _selectedFoods.value = updatedList!!
        Log.d("DetailViewModel", "Updated: ${food.description}, New Amount: $newAmount")
        Log.d("DetailViewModel", "Updated Foods List: ${_selectedFoods.value}")
    }

    fun getFoodsItemList(): List<FoodsItem> {
        return _selectedFoods.value?.map {
            FoodsItem(
                category = it.category,
                description = it.description,
                amount = it.amount
            )
        } ?: emptyList()
    }

    fun postNutrientData(age: Int) {
        val foodsList = getFoodsItemList()
        foodsList.forEach { food ->
            Log.d(
                "NutrientRequestLog",
                "Food: ${food.description}, Category: ${food.category}, Amount: ${food.amount}"
            )
        }

        val nutrientRequest = NutrientRequest(
            age = age,
            totalFoods = foodsList.size,
            foods = foodsList
        )

        Log.d("NutrientRequestLog", "NutrientRequest: Age: $age, TotalFoods: ${foodsList.size}, Foods: $foodsList")

        viewModelScope.launch {
            try {
                val response = ApiConfig.getRecommendation().getRecommendation(nutrientRequest)
                _recommendations.value = response

                val nutrisiYangHarusDipenuhi = response.nutrisiYangHarusDipenuhi
                val rekomendasi = response.rekomendasiMakananBerdasarkanContentBasedFiltering
                val selisihNutrisi = response.selisihNutrisi
                val nutrisiMakananYangTelahDikonsumsi = response.nutrisiMakananYangTelahDikonsumsi

                Log.d(
                    "NutrientRequestLog",
                    """
                    Response Summary:
                    - Nutrisi Dipenuhi: ${nutrisiYangHarusDipenuhi}
                    - Total Rekomendasi: ${rekomendasi}
                    - Selisih Nutrisi: ${selisihNutrisi}
                    - Nutrisi Konsumsi: ${nutrisiMakananYangTelahDikonsumsi}
                    """.trimIndent()
                )
                Log.d("NutrientRequestLog", "Response: $response")
            } catch (e: Exception) {
                Log.e("NutrientDetailViewModel", "Error posting nutrient data", e)
            }
        }
    }
}
