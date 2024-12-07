package com.example.nourishpath.data.api.nourishpath.request

import com.google.gson.annotations.SerializedName

data class NutrientRequest(

	@field:SerializedName("foods")
	val foods: List<FoodsItem>,

	@field:SerializedName("total_foods")
	val totalFoods: Int,

	@field:SerializedName("age")
	val age: Int
)

data class FoodsItem(
	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("amount")
	val amount: Float

)
