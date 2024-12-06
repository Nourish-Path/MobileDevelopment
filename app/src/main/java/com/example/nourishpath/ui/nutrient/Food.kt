package com.example.nourishpath.ui.nutrient

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val category: String,
    val description: String,
    var isSelected: Boolean = false,
    var amount: Float = 100f
): Parcelable
