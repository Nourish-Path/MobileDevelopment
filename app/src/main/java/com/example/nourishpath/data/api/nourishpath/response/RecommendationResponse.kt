package com.example.nourishpath.data.api.nourishpath.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecommendationResponse(

	@field:SerializedName("Nutrisi makanan yang telah dikonsumsi")
	val nutrisiMakananYangTelahDikonsumsi: NutrisiMakananYangTelahDikonsumsi,

	@field:SerializedName("Rekomendasi makanan berdasarkan Content-Based Filtering")
	val rekomendasiMakananBerdasarkanContentBasedFiltering: List<RekomendasiMakananBerdasarkanContentBasedFilteringItem>,

	@field:SerializedName("Nutrisi yang harus dipenuhi")
	val nutrisiYangHarusDipenuhi: NutrisiYangHarusDipenuhi,

	@field:SerializedName("Selisih nutrisi")
	val selisihNutrisi: SelisihNutrisi
) : Parcelable

@Parcelize
data class SelisihNutrisi(
	@field:SerializedName("Data.Vitamins.Vitamin K")
	val dataVitaminsVitaminK: Float,

	@field:SerializedName("Data.Vitamins.Vitamin B12")
	val dataVitaminsVitaminB12: Float,

	@field:SerializedName("Data.Major Minerals.Potassium")
	val dataMajorMineralsPotassium: Float,

	@field:SerializedName("Data.Major Minerals.Magnesium")
	val dataMajorMineralsMagnesium: Float,

	@field:SerializedName("Data.Major Minerals.Calcium")
	val dataMajorMineralsCalcium: Float,

	@field:SerializedName("Data.Riboflavin")
	val dataRiboflavin: Float,

	@field:SerializedName("Data.Protein")
	val dataProtein: Float,

	@field:SerializedName("Data.Major Minerals.Phosphorus")
	val dataMajorMineralsPhosphorus: Float,

	@field:SerializedName("Data.Vitamins.Vitamin B6")
	val dataVitaminsVitaminB6: Float,

	@field:SerializedName("Data.Choline")
	val dataCholine: Float,

	@field:SerializedName("Data.Niacin")
	val dataNiacin: Float,

	@field:SerializedName("Data.Major Minerals.Copper")
	val dataMajorMineralsCopper: Float,

	@field:SerializedName("Data.Fat.Polysaturated Fat")
	val dataFatPolysaturatedFat: Float,

	@field:SerializedName("Data.Fiber")
	val dataFiber: Float,

	@field:SerializedName("Data.Water")
	val dataWater: Float,

	@field:SerializedName("Data.Vitamins.Vitamin C")
	val dataVitaminsVitaminC: Float,

	@field:SerializedName("Data.Fat.Total Lipid")
	val dataFatTotalLipid: Float,

	@field:SerializedName("Data.Thiamin")
	val dataThiamin: Float,

	@field:SerializedName("Data.Vitamins.Vitamin E")
	val dataVitaminsVitaminE: Float,

	@field:SerializedName("Data.Carbohydrate")
	val dataCarbohydrate: Float,

	@field:SerializedName("Data.Major Minerals.Sodium")
	val dataMajorMineralsSodium: Float,

	@field:SerializedName("Data.Major Minerals.Iron")
	val dataMajorMineralsIron: Float,

	@field:SerializedName("Data.Vitamins.Vitamin A")
	val dataVitaminsVitaminA: Float,

	@field:SerializedName("Data.Major Minerals.Zinc")
	val dataMajorMineralsZinc: Float

): Parcelable

@Parcelize
data class NutrisiYangHarusDipenuhi(
	@field:SerializedName("Data.Vitamins.Vitamin K")
	val dataVitaminsVitaminK: Float,

	@field:SerializedName("Data.Vitamins.Vitamin B12")
	val dataVitaminsVitaminB12: Float,

	@field:SerializedName("Data.Major Minerals.Potassium")
	val dataMajorMineralsPotassium: Float,

	@field:SerializedName("Data.Major Minerals.Magnesium")
	val dataMajorMineralsMagnesium: Float,

	@field:SerializedName("Data.Major Minerals.Calcium")
	val dataMajorMineralsCalcium: Float,

	@field:SerializedName("Data.Riboflavin")
	val dataRiboflavin: Float,

	@field:SerializedName("Data.Protein")
	val dataProtein: Float,

	@field:SerializedName("Data.Major Minerals.Phosphorus")
	val dataMajorMineralsPhosphorus: Float,

	@field:SerializedName("Data.Vitamins.Vitamin B6")
	val dataVitaminsVitaminB6: Float,

	@field:SerializedName("Data.Choline")
	val dataCholine: Float,

	@field:SerializedName("Data.Niacin")
	val dataNiacin: Float,

	@field:SerializedName("Data.Major Minerals.Copper")
	val dataMajorMineralsCopper: Float,

	@field:SerializedName("Data.Fat.Polysaturated Fat")
	val dataFatPolysaturatedFat: Float,

	@field:SerializedName("Data.Fiber")
	val dataFiber: Float,

	@field:SerializedName("Data.Water")
	val dataWater: Float,

	@field:SerializedName("Data.Vitamins.Vitamin C")
	val dataVitaminsVitaminC: Float,

	@field:SerializedName("Data.Fat.Total Lipid")
	val dataFatTotalLipid: Float,

	@field:SerializedName("Data.Thiamin")
	val dataThiamin: Float,

	@field:SerializedName("Data.Vitamins.Vitamin E")
	val dataVitaminsVitaminE: Float,

	@field:SerializedName("Data.Carbohydrate")
	val dataCarbohydrate: Float,

	@field:SerializedName("Data.Major Minerals.Sodium")
	val dataMajorMineralsSodium: Float,

	@field:SerializedName("Data.Major Minerals.Iron")
	val dataMajorMineralsIron: Float,

	@field:SerializedName("Data.Vitamins.Vitamin A")
	val dataVitaminsVitaminA: Float,

	@field:SerializedName("Data.Major Minerals.Zinc")
	val dataMajorMineralsZinc: Float

): Parcelable

@Parcelize
data class NutrisiMakananYangTelahDikonsumsi(
	@field:SerializedName("Data.Vitamins.Vitamin K")
	val dataVitaminsVitaminK: Float,

	@field:SerializedName("Data.Vitamins.Vitamin B12")
	val dataVitaminsVitaminB12: Float,

	@field:SerializedName("Data.Major Minerals.Potassium")
	val dataMajorMineralsPotassium: Float,

	@field:SerializedName("Data.Major Minerals.Magnesium")
	val dataMajorMineralsMagnesium: Float,

	@field:SerializedName("Data.Major Minerals.Calcium")
	val dataMajorMineralsCalcium: Float,

	@field:SerializedName("Data.Riboflavin")
	val dataRiboflavin: Float,

	@field:SerializedName("Data.Protein")
	val dataProtein: Float,

	@field:SerializedName("Data.Major Minerals.Phosphorus")
	val dataMajorMineralsPhosphorus: Float,

	@field:SerializedName("Data.Vitamins.Vitamin B6")
	val dataVitaminsVitaminB6: Float,

	@field:SerializedName("Data.Choline")
	val dataCholine: Float,

	@field:SerializedName("Data.Niacin")
	val dataNiacin: Float,

	@field:SerializedName("Data.Major Minerals.Copper")
	val dataMajorMineralsCopper: Float,

	@field:SerializedName("Data.Fat.Polysaturated Fat")
	val dataFatPolysaturatedFat: Float,

	@field:SerializedName("Data.Fiber")
	val dataFiber: Float,

	@field:SerializedName("Data.Water")
	val dataWater: Float,

	@field:SerializedName("Data.Vitamins.Vitamin C")
	val dataVitaminsVitaminC: Float,

	@field:SerializedName("Data.Fat.Total Lipid")
	val dataFatTotalLipid: Float,

	@field:SerializedName("Data.Thiamin")
	val dataThiamin: Float,

	@field:SerializedName("Data.Vitamins.Vitamin E")
	val dataVitaminsVitaminE: Float,

	@field:SerializedName("Data.Carbohydrate")
	val dataCarbohydrate: Float,

	@field:SerializedName("Data.Major Minerals.Sodium")
	val dataMajorMineralsSodium: Float,

	@field:SerializedName("Data.Major Minerals.Iron")
	val dataMajorMineralsIron: Float,

	@field:SerializedName("Data.Vitamins.Vitamin A")
	val dataVitaminsVitaminA: Float,

	@field:SerializedName("Data.Major Minerals.Zinc")
	val dataMajorMineralsZinc: Float

): Parcelable

@Parcelize
data class RekomendasiMakananBerdasarkanContentBasedFilteringItem(

	@field:SerializedName("Relevance")
	val relevance: Float,

	@field:SerializedName("Category")
	val category: String,

	@field:SerializedName("Description")
	val description: String
): Parcelable
