package com.tcf.tcfspeedtester.presentation

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.wifi.SupplicantState
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.speedchecker.android.sdk.SpeedcheckerSDK
import com.tcf.tcfspeedtester.R
import com.tcf.tcfspeedtester.callback.SpeedTestCallbacks
import com.tcf.tcfspeedtester.data.database.client.AppDB
import com.tcf.tcfspeedtester.data.database.model.NetworkModel
import com.tcf.tcfspeedtester.databinding.ActivitySpeedTestBinding
import com.tcf.tcfspeedtester.service.TCFService
import com.tcf.tcfspeedtester.utils.*
import com.tcf.tcfspeedtester.utils.LocalPreferences.KeyValue.SpeedTest
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext
import java.io.File
import java.util.*

class SpeedTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpeedTestBinding
    private val database by lazy { AppDB.getFileDatabase(this).appDao() }
    private var time: Long = 0L
    private var schoolId = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        hideActionBarAndFullScreen()
        super.onCreate(savedInstanceState)
        binding = ActivitySpeedTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isNetworkAvailable()) {
            logI("internet", "before >> startAppService()")
            startAppService(this, TCFService::class.java)
            logI("internet", "after >> startAppService()")
        } else {
            toast("No connected available. Connect to internet")
        }
        //Commit
        time = 3 * 60 * 1000
        GlobalScope.launch(Dispatchers.IO) {
            delay(time)
        }

        logI("internet", "SpeedTestActivity()")

        initViews()

    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }

    override fun onResume() {
        super.onResume()
        initViews()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun initViews() {

        when {
            database != null -> {

                val getSchool = database.getSchoolById()
                val network = database.getTopSpeed()

                when {
                    network != null && getSchool != null -> {
                        var downSpeed = network.downloadSpeed
                        var upSpeed = network.uploadSpeed
                        binding.tvUpSpeedMeter.text = "$upSpeed"
                        binding.tvDownloadSpeedResult.text = "$downSpeed Mbps"
                        binding.tvUploadSpeedResult.text = "$upSpeed Mbps"
                        binding.tvNetworkName.text = getNetwork()
                        binding.tvCurrentTime.text = getCurrentTime()

                        var schoolName = getSchool.schoolName
                        binding.tvSchoolName.text = schoolName
                        schoolId = getSchool.schoolId
                    }
                }
            }
        }

        binding.shareSpeedStatus.setOnClickListener {
            if (database != null) {
                val dataList = database.getAllData()
                val school = database.getSchoolById()

                // Create the header string with formatted columns
                val header = String.format(
                    "%-10s | %-20s | %-15s | %-15s | %-10s | %-10s | %-20s | %-20s",
                    "School ID",
                    "School Name",
                    "Download Speed",
                    "Upload Speed",
                    "Latitude",
                    "Longitude",
                    "Network Name",
                    "Current Time"
                )

                // Join the header and data list
                val fileContents =
                    header + "\n" + dataList.joinToString(separator = "\n") { employee ->
                        String.format(
                            "%-10s | %-20s | %-15s | %-15s | %-10s | %-10s | %-20s | %-20s",
                            school.schoolId, school.schoolName, employee.downloadSpeed,
                            employee.uploadSpeed, employee.latitude, employee.longitude,
                            employee.networkName, employee.currentTime
                        )
                    }

                // Call the textFile function
                textFile(schoolId.toString(), fileContents)
            }
        }
    }

    private fun textFile(fileNameString: String, data: String) {

        // File create and write
        val fileName = "$fileNameString-File.txt"

        try {
            val fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Share file via Intent
        val file = File(filesDir, fileName)
        val uri = FileProvider.getUriForFile(this, "com.example.fileprovider", file)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(shareIntent, "Share via"))

    }


}