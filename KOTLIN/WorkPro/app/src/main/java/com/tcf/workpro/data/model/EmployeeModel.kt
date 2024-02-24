package com.tcf.workpro.data.model

import com.google.gson.annotations.SerializedName

data class EmployeeModel (
    @SerializedName("data" ) var data : ArrayList<EmployeeData> = arrayListOf()
)

data class EmployeeData (
    @SerializedName("employee_id" ) var employeeId : Int?    = null,
    @SerializedName("first_name"  ) var firstName  : String? = null,
    @SerializedName("job"         ) var job        : String? = null,
    @SerializedName("department"  ) var department : String? = null
)