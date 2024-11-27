package com.example.nourishpath

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nourishpath.databinding.ItemFoodBinding

class FoodSearchAdapter(
    private val foodList: List<String>,
    private val onFoodSelected: (String) -> Unit
) : RecyclerView.Adapter<FoodSearchAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: String) {
            binding.tvFoodName.text = food
            binding.root.setOnClickListener { onFoodSelected(food) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    fun updateData(newFoodList: List<String>) {
        (foodList as MutableList).clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = foodList.size
}
