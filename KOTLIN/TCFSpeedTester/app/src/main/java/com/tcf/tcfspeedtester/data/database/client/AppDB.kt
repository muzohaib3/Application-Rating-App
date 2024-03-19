package com.tcf.tcfspeedtester.data.database.client

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tcf.tcfspeedtester.data.database.dao.AppDao
import com.tcf.tcfspeedtester.data.database.model.NetworkModel
import com.tcf.tcfspeedtester.data.database.model.SchoolModel
import com.tcf.tcfspeedtester.utils.logI

@Database(entities =
[
    NetworkModel::class, SchoolModel::class
], version = 5, exportSchema = false)
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