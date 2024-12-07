package com.example.nourishpath.ui.nutrient.recommendation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nourishpath.MainActivity
import com.example.nourishpath.R
import com.example.nourishpath.data.api.nourishpath.response.RecommendationResponse
import com.example.nourishpath.databinding.ActivityRecommendationBinding

class RecommendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationBinding
    val viewModel by viewModels<RecommendationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val recommendations = intent.getParcelableExtra<RecommendationResponse>("RECOMMENDATIONS")
        Log.d("RecommendationActivity", "Received recommendations: $recommendations")


        if (recommendations != null) {
            viewModel.setRecommendationResponse(recommendations)
        }

        val adapter = RecommendationAdapter()
        binding.recyclerViewRecommendations.adapter = adapter
        binding.recyclerViewRecommendations.layoutManager = LinearLayoutManager(this)

        viewModel.recommendationResponse.observe(this) { response ->
            val recommendationList = response.rekomendasiMakananBerdasarkanContentBasedFiltering
            adapter.submitList(recommendationList)
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}