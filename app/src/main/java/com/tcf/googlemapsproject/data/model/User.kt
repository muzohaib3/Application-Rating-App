package com.tcf.googlemapsproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mapdata")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "name") var name:String,
    @ColumnInfo(name = "email") var email:String,
    @ColumnInfo(name = "password") var password:String,
    @ColumnInfo(name = "phNo") var phNo:String,
)