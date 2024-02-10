package com.tcf.googlemapsproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_rating")
data class AppRating(
    @PrimaryKey
    @ColumnInfo(name = "latitude") var latitude:Double,
    @ColumnInfo(name = "longitude") var longitude:Double,
    @ColumnInfo(name = "whatsapp_rate") var whatsapp_rate:Double,
    @ColumnInfo(name = "youtube_rate") var youtube_rate:Double,
    @ColumnInfo(name = "linkedin_rate") var linkedin_rate:Double,
    @ColumnInfo(name = "twitter_rate") var twitter_rate:Double,
    @ColumnInfo(name = "facebook_rate") var facebook_rate:Double,
    @ColumnInfo(name = "insta_rate") var insta_rate:Double
)