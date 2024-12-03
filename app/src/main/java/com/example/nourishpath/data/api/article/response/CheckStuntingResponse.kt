package com.example.nourishpath.data.api.article.response

import com.google.gson.annotations.SerializedName

data class CheckStuntingResponse(
	@field:SerializedName("model_prediction")
	val modelPrediction: List<Int>,

	@field:SerializedName("stunting_status")
	val stuntingStatus: String
)
