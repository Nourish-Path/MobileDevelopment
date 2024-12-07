package com.example.nourishpath.ui.nutrient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nourishpath.R
import com.example.nourishpath.databinding.RowFoodBinding

class FoodAdapter : ListAdapter<Food, FoodAdapter.FoodViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var onItemRemoveCallback: OnItemRemoveCallback

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

    class FoodViewHolder(val binding: RowFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(food: Food) {
            binding.tvFoodName.text = food.description
            binding.root.setBackgroundResource(
                if (food.isSelected) R.drawable.selected_item_background else R.drawable.food_list_background
            )
            binding.btnRemove.visibility = if (food.isSelected) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = RowFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = getItem(position)
        holder.bindData(food)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(food)
        }
        holder.binding.btnRemove.setOnClickListener {
            onItemRemoveCallback.onItemRemoved(food)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Food)
    }

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        this.onItemClickCallback = callback
    }

    interface OnItemRemoveCallback {
        fun onItemRemoved(data: Food)
    }

    fun setOnItemRemoveCallback(callback: OnItemRemoveCallback) {
        this.onItemRemoveCallback = callback
    }
}