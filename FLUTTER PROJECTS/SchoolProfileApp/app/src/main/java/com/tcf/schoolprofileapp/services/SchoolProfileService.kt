package com.tcf.schoolprofileapp.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers

interface SchoolProfileService {

    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: \"http://tempuri.org/GetAllActiveSchools\""
    )
    @GET("/SchoolProfileService/Service1.asmx")
    fun getAllActiveSchools(@Body requestBody: String): Call<List<School>>


}