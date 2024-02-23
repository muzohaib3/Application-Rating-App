package com.tcf.speedup.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tcf.speedup.data.local.dao.AppDao
import com.tcf.speedup.data.models.NetworkModel

@Database(entities =
[
    NetworkModel::class
], version = 1, exportSchema = false)
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
                    .databaseBuilder(context, AppDB::class.java, "InternetSpeed")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE
        }
    }
}