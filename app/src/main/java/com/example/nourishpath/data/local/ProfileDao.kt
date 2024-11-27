package com.example.nourishpath.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: Profile)

    @Query("SELECT * FROM Profile LIMIT 1")
    suspend fun getProfile(): Profile?

    @Query("DELETE FROM Profile")
    suspend fun deleteAll()
}