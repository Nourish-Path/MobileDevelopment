package com.example.nourishpath.ui.nutrient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nourishpath.R

class NutrientTableAdapter(
    private val nutrientData: Map<String, Double>
) : RecyclerView.Adapter<NutrientTableAdapter.NutrientViewHolder>() {

    inner class NutrientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNutrientName: TextView = view.findViewById(R.id.tvNutrientName)
        val tvNutrientValue: TextView = view.findViewById(R.id.tvNutrientValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutrientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nutrient_table, parent, false)
        return NutrientViewHolder(view)
    }

    override fun onBindViewHolder(holder: NutrientViewHolder, position: Int) {
        val nutrientName = nutrientData.keys.elementAt(position)
        val nutrientValue = nutrientData[nutrientName]

        holder.tvNutrientName.text = nutrientName
        holder.tvNutrientValue.text = "%.2f".format(nutrientValue) // Format ke 2 desimal
    }

    override fun getItemCount(): Int {
        return nutrientData.size
    }
}
