package com.example.nourishpath.ui.nutrient.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nourishpath.databinding.RowDetailBinding
import com.example.nourishpath.ui.nutrient.Food

class DetailAdapter(private val onAmountChanged: (Food, Float) -> Unit): ListAdapter<Food, DetailAdapter.DetailViewHolder>(DIFF_CALLBACK) {
    inner class DetailViewHolder(private val binding: RowDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            binding.foodName.text = food.description
            binding.foodCategory.text = food.category
            binding.amount.setText(food.amount.toString())

            binding.amount.addTextChangedListener {
                val newAmount = it.toString().toFloatOrNull() ?: 0f
                if (food.amount != newAmount) {
                    food.amount = newAmount
                    onAmountChanged(food, newAmount)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = RowDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val food = getItem(position)
        holder.bind(food)
    }
    override fun getItemCount() = currentList.size

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Food>() {
            override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem.description == newItem.description
            }

            override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem.description == newItem.description && oldItem.isSelected == newItem.isSelected
            }
        }
    }
}