package com.example.nourishpath.ui.nutrient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nourishpath.R

class FoodListAdapter(private var foodList: List<String>) : RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

    private var filteredList = foodList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            foodList.toMutableList()
        } else {
            foodList.filter { it.contains(query, ignoreCase = true) }.toMutableList()
        }
        notifyDataSetChanged()
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(foodName: String) {
            itemView.findViewById<TextView>(R.id.tvFoodName).text = foodName
        }
    }
}