package com.example.nourishpath.ui.nutrient.recommendation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nourishpath.data.api.nourishpath.response.RecommendationResponse

class RecommendationViewModel: ViewModel() {
    private val _recommendationResponse = MutableLiveData<RecommendationResponse>()
    val recommendationResponse: MutableLiveData<RecommendationResponse> = _recommendationResponse

    fun setRecommendationResponse(response: RecommendationResponse) {
        _recommendationResponse.value = response
    }
}