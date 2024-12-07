package com.example.nourishpath.ui.nutrient.detail

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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

            binding.amount.removeTextChangedListener(binding.amount.tag as? TextWatcher)

            binding.amount.setText(food.amount.toString())

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val newAmount = s.toString().toFloatOrNull() ?: 0f
                    if (food.amount != newAmount) {
                        food.amount = newAmount
                        onAmountChanged(food, newAmount)
                    }
                }
            }

            binding.amount.addTextChangedListener(textWatcher)
            binding.amount.tag = textWatcher
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