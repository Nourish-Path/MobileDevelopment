package com.example.nourishpath.data.local

import androidx.room.TypeConverter
import android.net.Uri

class Converter {
    // Mengonversi Uri menjadi String
    @TypeConverter
    fun fromUri(uri: Uri?): String? {
        return uri?.toString() // Mengonversi Uri menjadi String
    }

    // Mengonversi String menjadi Uri
    @TypeConverter
    fun toUri(uriString: String?): Uri? {
        return uriString?.let { Uri.parse(it) } // Mengonversi String kembali menjadi Uri
    }
}