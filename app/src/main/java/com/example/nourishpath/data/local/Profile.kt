package com.example.nourishpath.data.local
import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Profile")
data class Profile(
    @PrimaryKey val phone: String,
    val profileImageUri: String,
    val name: String,
    val address: String
): Parcelable