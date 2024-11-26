package com.example.nourishpath.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Profile::class],  version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ProfileRoomDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var instance: ProfileRoomDatabase? = null
        fun getInstance(context: Context): ProfileRoomDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ProfileRoomDatabase::class.java, "Events.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
    }
}