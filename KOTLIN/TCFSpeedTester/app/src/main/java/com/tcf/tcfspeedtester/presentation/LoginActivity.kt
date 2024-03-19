package com.tcf.tcfspeedtester.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.speedchecker.android.sdk.SpeedcheckerSDK
import com.tcf.tcfspeedtester.callback.SpeedTestCallbacks
import com.tcf.tcfspeedtester.data.database.client.AppDB
import com.tcf.tcfspeedtester.data.database.model.NetworkModel
import com.tcf.tcfspeedtester.data.database.model.SchoolModel
import com.tcf.tcfspeedtester.databinding.ActivityLoginBinding
import com.tcf.tcfspeedtester.service.TCFService
import com.tcf.tcfspeedtester.utils.*
import com.tcf.tcfspeedtester.utils.LocalPreferences.KeyValue.SpeedTest
import com.tcf.tcfspeedtester.utils.LocalPreferences.KeyValue.isLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var speedTestCallbacks: SpeedTestCallbacks
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val database by lazy { AppDB.getFileDatabase(this).appDao() }
    private val preferences = LocalPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        hideActionBarAndFullScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        speedTestCallbacks = SpeedTestCallbacks(this)
        SpeedcheckerSDK.init(this);
        SpeedcheckerSDK.askPermissions(this)

        /** onclick function **/

        binding.btSubmit.setOnClickListener {
            SpeedcheckerSDK.SpeedTest.setOnSpeedTestListener(speedTestCallbacks)
            SpeedcheckerSDK.SpeedTest.startTest(this)
            loginUser()
        }

    }

    private fun loginUser(){

        var id = binding.etSchoolId.text.toString().trim()
        var name = binding.etSchoolName.text.toString().trim()

        when{
            id.isNullOrEmpty()->{
                toast("school id is missing")
            }
            name.isNullOrEmpty()->{
                toast("school name is missing")
            }
            else->{
                var idInt = id.toInt()
                insertSchoolStatus(idInt,name)
            }
        }
    }

    private fun insertSchoolStatus(id:Int, name:String){

        if (!name.isNullOrEmpty() || !id.equals(null))
        {
            logI(SpeedTest,"login >> name = $name , id = $id")

            val school = SchoolModel(id,name, getCurrentTime(),true)
            database.insertSchool(school)
            gotoActivity(SpeedTestActivity::class.java)
            finish()

        }
        else{
            toast("name and id not found")
        }

    }

}