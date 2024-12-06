package com.example.nourishpath.data.api.nourishpath.request

import com.google.gson.annotations.SerializedName

data class CheckStuntingRequest(
    @SerializedName("age") val age: Int,
    @SerializedName("height") val height: Float,
    @SerializedName("weight") val weight: Float
)
