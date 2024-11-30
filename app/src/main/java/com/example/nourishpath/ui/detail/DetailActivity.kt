package com.example.nourishpath.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.nourishpath.R
import com.example.nourishpath.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(ARTICLE_ID, -1)

        if (id != -1) {
            val detailViewModel: DetailViewModel by viewModels {
                object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return DetailViewModel(id) as T
                    }
                }
            }
            detailViewModel.isLoading.observe(this) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
            detailViewModel.article.observe(this) { article ->
                Glide.with(this)
                    .load(article.image)
                    .into(binding.imgItemPhoto)
                binding.titleDetail.text = article.title
                binding.description.text = article.description
            }
        }
    }

    companion object {
        const val ARTICLE_ID = "EXTRA_ARTICLE"
    }
}