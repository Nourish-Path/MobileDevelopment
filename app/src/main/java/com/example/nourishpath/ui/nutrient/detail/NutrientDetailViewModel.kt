package com.example.nourishpath.ui.nutrient.detail

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.viewModelScope
import com.example.nourishpath.data.api.nourishpath.ApiConfig
import com.example.nourishpath.data.api.nourishpath.request.FoodsItem
import com.example.nourishpath.data.api.nourishpath.request.NutrientRequest
import com.example.nourishpath.data.api.nourishpath.response.RecommendationResponse
import com.example.nourishpath.ui.nutrient.Food
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException

class NutrientDetailViewModel : ViewModel() {
    private val _selectedFoods = MutableLiveData<List<Food>>()
    val selectedFoods: LiveData<List<Food>> = _selectedFoods

    private val _recommendations = MutableLiveData<RecommendationResponse>()
    val recommendations: LiveData<RecommendationResponse> get() = _recommendations

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

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
        _isLoading.value = true
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
            } catch (e: SocketTimeoutException) {
                _errorMessage.value = "Connection Timeout, Try Again."
            } catch (e: IOException) {
                _errorMessage.value = "Your internet has problems. Check your connection."
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred. Please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }
}
