package com.tcf.speedup.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tcf.speedup.data.models.NetworkModel

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAppRecords(data:NetworkModel)

    @Query("select * from network_data")
    fun getAllData():List<NetworkModel>
}