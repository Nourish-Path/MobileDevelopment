package com.example.nourishpath.ui.nutrient.models

import android.os.Parcel
import android.os.Parcelable

data class NutrientData(
    val totalNutrients: HashMap<String, Double>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readSerializable() as HashMap<String, Double>
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(totalNutrients)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<NutrientData> {
        override fun createFromParcel(parcel: Parcel): NutrientData {
            return NutrientData(parcel)
        }

        override fun newArray(size: Int): Array<NutrientData?> {
            return arrayOfNulls(size)
        }
    }
}
