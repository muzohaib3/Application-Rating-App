package com.tcf.tcfspeedtester.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tcf.tcfspeedtester.data.database.model.NetworkModel
import com.tcf.tcfspeedtester.data.database.model.SchoolModel

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertData(networkModel: NetworkModel)

    @Query("select * from network_data")
    fun getAllData():List<NetworkModel>

    @Query("SELECT * FROM network_data ORDER BY id DESC LIMIT 1")
    fun getTopSpeed():NetworkModel


    /** school **/

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertSchool(schoolModel: SchoolModel)

    @Query("select * from school_data")
    fun getSchoolById():SchoolModel



}