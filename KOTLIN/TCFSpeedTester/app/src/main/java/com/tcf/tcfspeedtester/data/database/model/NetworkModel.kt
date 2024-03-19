package com.tcf.tcfspeedtester.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "network_data")
data class NetworkModel(
    @ColumnInfo(name = "latitude") var latitude:Double,
    @ColumnInfo(name = "longitude") var longitude:Double,
    @ColumnInfo(name = "downloadSpeed") var downloadSpeed:Double,
    @ColumnInfo(name = "uploadSpeed") var uploadSpeed:Double,
    @ColumnInfo(name = "current_time") var currentTime:String,
    @ColumnInfo(name = "network_name") var networkName:String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Int = 0
}