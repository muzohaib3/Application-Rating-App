package com.tcf.googlemapsproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tcf.googlemapsproject.data.model.AppRating
import com.tcf.googlemapsproject.data.model.User

@Dao
interface AppDao {
    @Query("SELECT * FROM mapdata")
    fun getAll(): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(details: User)
    @Query("SELECT * FROM mapdata where email=:email")
    fun getEmailPassword(email:String): User

    @Query("SELECT * FROM app_rating")
    fun getAllRatingData(): AppRating

    @Query("SELECT * FROM app_rating")
    fun getAllRating(): List<AppRating>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAppData(appRate: AppRating)


}