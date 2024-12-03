package com.example.nourishpath.data.api.article.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

	@field:SerializedName("Nutrisi makanan yang telah dikonsumsi")
	val nutrisiMakananYangTelahDikonsumsi: NutrisiMakananYangTelahDikonsumsi,

	@field:SerializedName("Rekomendasi makanan berdasarkan Content-Based Filtering")
	val rekomendasiMakananBerdasarkanContentBasedFiltering: List<RekomendasiMakananBerdasarkanContentBasedFilteringItem>,

	@field:SerializedName("Nutrisi yang harus dipenuhi")
	val nutrisiYangHarusDipenuhi: NutrisiYangHarusDipenuhi,

	@field:SerializedName("Selisih nutrisi")
	val selisihNutrisi: SelisihNutrisi
)

data class NutrisiYangHarusDipenuhi(

	@field:SerializedName("Data.Vitamins.Vitamin K")
	val dataVitaminsVitaminK: Int,

	@field:SerializedName("Data.Vitamins.Vitamin B12")
	val dataVitaminsVitaminB12: Any,

	@field:SerializedName("Data.Major Minerals.Potassium")
	val dataMajorMineralsPotassium: Int,

	@field:SerializedName("Data.Major Minerals.Magnesium")
	val dataMajorMineralsMagnesium: Int,

	@field:SerializedName("Data.Major Minerals.Calcium")
	val dataMajorMineralsCalcium: Int,

	@field:SerializedName("Data.Riboflavin")
	val dataRiboflavin: Any,

	@field:SerializedName("Data.Protein")
	val dataProtein: Int,

	@field:SerializedName("Data.Major Minerals.Phosphorus")
	val dataMajorMineralsPhosphorus: Int,

	@field:SerializedName("Data.Vitamins.Vitamin B6")
	val dataVitaminsVitaminB6: Any,

	@field:SerializedName("Data.Choline")
	val dataCholine: Int,

	@field:SerializedName("Data.Niacin")
	val dataNiacin: Int,

	@field:SerializedName("Data.Major Minerals.Copper")
	val dataMajorMineralsCopper: Any,

	@field:SerializedName("Data.Fat.Polysaturated Fat")
	val dataFatPolysaturatedFat: Any,

	@field:SerializedName("Data.Fiber")
	val dataFiber: Int,

	@field:SerializedName("Data.Water")
	val dataWater: Int,

	@field:SerializedName("Data.Vitamins.Vitamin C")
	val dataVitaminsVitaminC: Int,

	@field:SerializedName("Data.Fat.Total Lipid")
	val dataFatTotalLipid: Int,

	@field:SerializedName("Data.Thiamin")
	val dataThiamin: Any,

	@field:SerializedName("Data.Vitamins.Vitamin E")
	val dataVitaminsVitaminE: Int,

	@field:SerializedName("Data.Carbohydrate")
	val dataCarbohydrate: Int,

	@field:SerializedName("Data.Major Minerals.Sodium")
	val dataMajorMineralsSodium: Int,

	@field:SerializedName("Data.Major Minerals.Iron")
	val dataMajorMineralsIron: Any,

	@field:SerializedName("Data.Vitamins.Vitamin A")
	val dataVitaminsVitaminA: Int,

	@field:SerializedName("Data.Major Minerals.Zinc")
	val dataMajorMineralsZinc: Any
)

data class SelisihNutrisi(

	@field:SerializedName("Data.Vitamins.Vitamin K")
	val dataVitaminsVitaminK: Any,

	@field:SerializedName("Data.Vitamins.Vitamin B12")
	val dataVitaminsVitaminB12: Any,

	@field:SerializedName("Data.Major Minerals.Potassium")
	val dataMajorMineralsPotassium: Int,

	@field:SerializedName("Data.Major Minerals.Magnesium")
	val dataMajorMineralsMagnesium: Int,

	@field:SerializedName("Data.Major Minerals.Calcium")
	val dataMajorMineralsCalcium: Int,

	@field:SerializedName("Data.Riboflavin")
	val dataRiboflavin: Any,

	@field:SerializedName("Data.Protein")
	val dataProtein: Any,

	@field:SerializedName("Data.Major Minerals.Phosphorus")
	val dataMajorMineralsPhosphorus: Int,

	@field:SerializedName("Data.Vitamins.Vitamin B6")
	val dataVitaminsVitaminB6: Any,

	@field:SerializedName("Data.Choline")
	val dataCholine: Any,

	@field:SerializedName("Data.Niacin")
	val dataNiacin: Any,

	@field:SerializedName("Data.Major Minerals.Copper")
	val dataMajorMineralsCopper: Any,

	@field:SerializedName("Data.Fat.Polysaturated Fat")
	val dataFatPolysaturatedFat: Any,

	@field:SerializedName("Data.Fiber")
	val dataFiber: Int,

	@field:SerializedName("Data.Water")
	val dataWater: Any,

	@field:SerializedName("Data.Vitamins.Vitamin C")
	val dataVitaminsVitaminC: Int,

	@field:SerializedName("Data.Fat.Total Lipid")
	val dataFatTotalLipid: Any,

	@field:SerializedName("Data.Thiamin")
	val dataThiamin: Any,

	@field:SerializedName("Data.Vitamins.Vitamin E")
	val dataVitaminsVitaminE: Any,

	@field:SerializedName("Data.Carbohydrate")
	val dataCarbohydrate: Any,

	@field:SerializedName("Data.Major Minerals.Sodium")
	val dataMajorMineralsSodium: Int,

	@field:SerializedName("Data.Major Minerals.Iron")
	val dataMajorMineralsIron: Any,

	@field:SerializedName("Data.Vitamins.Vitamin A")
	val dataVitaminsVitaminA: Int,

	@field:SerializedName("Data.Major Minerals.Zinc")
	val dataMajorMineralsZinc: Any
)

data class RekomendasiMakananBerdasarkanContentBasedFilteringItem(

	@field:SerializedName("Relevance")
	val relevance: Any,

	@field:SerializedName("Category")
	val category: String,

	@field:SerializedName("Description")
	val description: String
)

data class NutrisiMakananYangTelahDikonsumsi(

	@field:SerializedName("Data.Vitamins.Vitamin K")
	val dataVitaminsVitaminK: Any,

	@field:SerializedName("Data.Vitamins.Vitamin B12")
	val dataVitaminsVitaminB12: Any,

	@field:SerializedName("Data.Major Minerals.Potassium")
	val dataMajorMineralsPotassium: Int,

	@field:SerializedName("Data.Major Minerals.Magnesium")
	val dataMajorMineralsMagnesium: Int,

	@field:SerializedName("Data.Major Minerals.Calcium")
	val dataMajorMineralsCalcium: Int,

	@field:SerializedName("Data.Riboflavin")
	val dataRiboflavin: Any,

	@field:SerializedName("Data.Protein")
	val dataProtein: Any,

	@field:SerializedName("Data.Major Minerals.Phosphorus")
	val dataMajorMineralsPhosphorus: Int,

	@field:SerializedName("Data.Vitamins.Vitamin B6")
	val dataVitaminsVitaminB6: Any,

	@field:SerializedName("Data.Choline")
	val dataCholine: Any,

	@field:SerializedName("Data.Niacin")
	val dataNiacin: Any,

	@field:SerializedName("Data.Major Minerals.Copper")
	val dataMajorMineralsCopper: Int,

	@field:SerializedName("Data.Fat.Polysaturated Fat")
	val dataFatPolysaturatedFat: Any,

	@field:SerializedName("Data.Fiber")
	val dataFiber: Int,

	@field:SerializedName("Data.Water")
	val dataWater: Any,

	@field:SerializedName("Data.Vitamins.Vitamin C")
	val dataVitaminsVitaminC: Int,

	@field:SerializedName("Data.Fat.Total Lipid")
	val dataFatTotalLipid: Any,

	@field:SerializedName("Data.Thiamin")
	val dataThiamin: Any,

	@field:SerializedName("Data.Vitamins.Vitamin E")
	val dataVitaminsVitaminE: Any,

	@field:SerializedName("Data.Carbohydrate")
	val dataCarbohydrate: Any,

	@field:SerializedName("Data.Major Minerals.Sodium")
	val dataMajorMineralsSodium: Int,

	@field:SerializedName("Data.Major Minerals.Iron")
	val dataMajorMineralsIron: Int,

	@field:SerializedName("Data.Vitamins.Vitamin A")
	val dataVitaminsVitaminA: Int,

	@field:SerializedName("Data.Major Minerals.Zinc")
	val dataMajorMineralsZinc: Any
)
