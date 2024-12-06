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

    fun updateAmount(food: Food, amount: Float) {
        _selectedFoods.value = _selectedFoods.value?.map {
            if (it == food) it.copy(amount = amount) else it
        }
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

    fun fetchRecommendations(nutrientRequest: NutrientRequest) {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getRecommendation().getRecommendation(nutrientRequest)
                _recommendations.postValue(response) // Mengupdate LiveData dengan hasil response
            } catch (e: Exception) {
                Log.e("RecommendationViewModel", "Error fetching recommendations", e)
            }
        }
    }
}
