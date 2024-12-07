package com.example.nourishpath.ui.nutrient.nutritionfacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nourishpath.data.api.nourishpath.response.RecommendationResponse

class NutritionFactsViewModel: ViewModel() {
    private val _recommendations = MutableLiveData<RecommendationResponse>()
    val recommendations: LiveData<RecommendationResponse> = _recommendations

    fun setRecommendations(recommendations: RecommendationResponse) {
        _recommendations.value = recommendations
    }
}