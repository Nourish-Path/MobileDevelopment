package com.example.nourishpath.ui.nutrient.nutritionfacts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nourishpath.R
import com.example.nourishpath.data.api.nourishpath.response.RecommendationResponse
import com.example.nourishpath.databinding.ActivityNutritionFactsBinding
import com.example.nourishpath.ui.nutrient.recommendation.RecommendationActivity

class NutritionFactsActivity : AppCompatActivity() {
    private val viewModel by viewModels<NutritionFactsViewModel>()
    private lateinit var binding: ActivityNutritionFactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNutritionFactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val recommendations = intent.getParcelableExtra<RecommendationResponse>("RECOMMENDATIONS")
        Log.d("NutritionFactsActivity", "Received recommendations: $recommendations")

        if (recommendations != null) {
            viewModel.setRecommendations(recommendations)
        }
        viewModel.recommendations.observe(this) { recommendations ->
            bindValues(recommendations)
        }
        binding.btnRecommendations.setOnClickListener {
            val intent = Intent(this, RecommendationActivity::class.java).apply {
                putExtra("RECOMMENDATIONS", recommendations)
            }
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindValues(recommendations: RecommendationResponse) {
        binding.apply {
            tvVitaminAValue.text = recommendations.nutrisiMakananYangTelahDikonsumsi.dataVitaminsVitaminA.toString() + " mcg"
            tvVitaminB12Value.text = recommendations.nutrisiMakananYangTelahDikonsumsi.dataVitaminsVitaminB12.toString() + " mcg"
            tvVitaminCValue.text = recommendations.nutrisiMakananYangTelahDikonsumsi.dataVitaminsVitaminC.toString() + " mg"
            tvCalciumValue.text = recommendations.nutrisiMakananYangTelahDikonsumsi.dataMajorMineralsCalcium.toString() + " mg"
            tvVitaminKValue.text = recommendations.nutrisiMakananYangTelahDikonsumsi.dataVitaminsVitaminK.toString() + " mcg"
            tvIronValue.text = recommendations.nutrisiMakananYangTelahDikonsumsi.dataMajorMineralsIron.toString() + " mg"
            tvProteinValue.text = recommendations.nutrisiMakananYangTelahDikonsumsi.dataProtein.toString() + " g"
            tvZincValue.text = recommendations.nutrisiMakananYangTelahDikonsumsi.dataMajorMineralsZinc.toString() + " mg"
            tvCarbohydrateValue.text = recommendations.nutrisiMakananYangTelahDikonsumsi.dataCarbohydrate.toString() + " g"
            tvTotalLipidValue.text = recommendations.nutrisiMakananYangTelahDikonsumsi.dataFatTotalLipid.toString() + " g"

            tvNeededVitaminAValue.text = recommendations.selisihNutrisi.dataVitaminsVitaminA.toString() + " mcg"
            tvNeededVitaminB12Value.text = recommendations.selisihNutrisi.dataVitaminsVitaminB12.toString() + " mcg"
            tvNeededVitaminCValue.text = recommendations.selisihNutrisi.dataVitaminsVitaminC.toString() + " mg"
            tvNeededCalciumValue.text = recommendations.selisihNutrisi.dataMajorMineralsCalcium.toString() + " mg"
            tvNeededVitaminKValue.text = recommendations.selisihNutrisi.dataVitaminsVitaminK.toString() + " mcg"
            tvNeededIronValue.text = recommendations.selisihNutrisi.dataMajorMineralsIron.toString() + " mg"
            tvNeededProteinValue.text = recommendations.selisihNutrisi.dataProtein.toString() + " g"
            tvNeededZincValue.text = recommendations.selisihNutrisi.dataMajorMineralsZinc.toString() + " mg"
            tvNeededCarbohydrateValue.text = recommendations.nutrisiMakananYangTelahDikonsumsi.dataCarbohydrate.toString() + " g"
            tvNeededTotalLipidValue.text = recommendations.nutrisiMakananYangTelahDikonsumsi.dataFatTotalLipid.toString() + " g"
        }
    }
}