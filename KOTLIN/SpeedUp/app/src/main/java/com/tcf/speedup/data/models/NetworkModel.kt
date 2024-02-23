package com.tcf.speedup.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "network_data")
data class NetworkModel(

    @ColumnInfo(name = "latitude") var latitude:Double,
    @ColumnInfo(name = "longitude") var longitude:Double,
    @ColumnInfo(name = "downloadSpeed") var downloadSpeed:Int,
    @ColumnInfo(name = "uploadSpeed") var uploadSpeed:Int,
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Int = 0
}