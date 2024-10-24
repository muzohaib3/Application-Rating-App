package com.tcf.schoolprofileapp.services

data class School(
    val SchoolID: Int,
    val Region: String,
    val Area: String,
    val School: String,
    val Ecompound: String?,
    val Cluster: String?,
    val schoolLocalName: String?,
    val District: String,
    val Town: String,
    val Province: String,
    val Units: Int,
    val establishmentYear: Int,
    val Longitude: Double,
    val Latitude: Double,
    val EmpId: String?,
    val Principal: String?,
    val principalContact: String?,
    val OperationalUtilization: Int?,
    val totalStrength: Int,
    val FemaleRatio: Int
)
