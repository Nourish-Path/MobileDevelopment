package com.example.nourishpath.ui.nutrient.recommendation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nourishpath.data.api.nourishpath.response.RekomendasiMakananBerdasarkanContentBasedFilteringItem
import com.example.nourishpath.databinding.RowRecommendationBinding

class RecommendationAdapter : ListAdapter<RekomendasiMakananBerdasarkanContentBasedFilteringItem, RecommendationAdapter.RecommendationViewHolder>(RecommendationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = RowRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class RecommendationViewHolder(private val binding: RowRecommendationBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "DefaultLocale")
        fun bind(item: RekomendasiMakananBerdasarkanContentBasedFilteringItem) {
            binding.tvCategory.text = "Category: ${item.category}"
            binding.tvDescription.text = "Description: ${item.description}"
            binding.tvRelevance.text = "Relevance: ${String.format("%.2f", item.relevance * 100)}%"
        }
    }

    class RecommendationDiffCallback : DiffUtil.ItemCallback<RekomendasiMakananBerdasarkanContentBasedFilteringItem>() {
        override fun areItemsTheSame(oldItem: RekomendasiMakananBerdasarkanContentBasedFilteringItem, newItem: RekomendasiMakananBerdasarkanContentBasedFilteringItem): Boolean {
            return oldItem.category == newItem.category
        }

        override fun areContentsTheSame(oldItem: RekomendasiMakananBerdasarkanContentBasedFilteringItem, newItem: RekomendasiMakananBerdasarkanContentBasedFilteringItem): Boolean {
            return oldItem == newItem
        }
    }
}
