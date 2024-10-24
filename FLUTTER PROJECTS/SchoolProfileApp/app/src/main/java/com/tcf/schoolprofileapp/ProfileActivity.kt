package com.tcf.schoolprofileapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tcf.schoolprofileapp.services.School
import com.tcf.schoolprofileapp.services.SchoolProfileService
import com.tcf.schoolprofileapp.services.SoapRequestBody.soapRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        main()
    }

    fun main() {
        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://202.142.180.137/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SchoolProfileService::class.java)

        // Make API call
        val call = service.getAllActiveSchools(soapRequestBody)
        call.enqueue(object : Callback<List<School>> {
            override fun onResponse(call: Call<List<School>>, response: Response<List<School>>) {
                if (response.isSuccessful) {
                    val schools = response.body()
                    schools?.forEach { school ->
                        println("School: ${school.School}")
                        println("Principal: ${school.Principal}")
                        println("Region: ${school.Region}")
                        println("Total Strength: ${school.totalStrength}")
                        println("---------------------------")
                    }
                } else {
                    println("Failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<School>>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }

}