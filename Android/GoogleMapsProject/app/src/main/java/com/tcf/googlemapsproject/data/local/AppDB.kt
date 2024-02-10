package com.tcf.googlemapsproject.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tcf.googlemapsproject.data.model.AppRating
import com.tcf.googlemapsproject.data.model.User

@Database(entities =
[
    User::class, AppRating::class
], version = 4, exportSchema = false)
abstract class AppDB : RoomDatabase()
{
    abstract fun appDao()    : AppDao
    companion object
    {
        private lateinit var INSTANCE: AppDB

        fun getFileDatabase(context: Context): AppDB
        {
            if (!this::INSTANCE.isInitialized)
            {
                INSTANCE = Room
                    .databaseBuilder(context, AppDB::class.java, "MapView")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE
        }
    }
}