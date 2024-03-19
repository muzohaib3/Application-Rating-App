package com.tcf.tcfspeedtester.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "school_data")
data class SchoolModel(
    @ColumnInfo(name = "schoolId") var schoolId:Int,
    @ColumnInfo(name = "schoolName") var schoolName:String,
    @ColumnInfo(name = "current_time") var current_time:String,
    @ColumnInfo(name = "isLogin") var isLogin:Boolean,
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Int = 0
}