package com.tcf.tcfspeedtester.callback

import android.content.Context
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.speedchecker.android.sdk.Public.SpeedTestListener
import com.speedchecker.android.sdk.Public.SpeedTestResult
import com.speedchecker.android.sdk.SpeedcheckerSDK
import com.tcf.tcfspeedtester.data.database.client.AppDB
import com.tcf.tcfspeedtester.data.database.model.NetworkModel
import com.tcf.tcfspeedtester.utils.getCurrentLocation
import com.tcf.tcfspeedtester.utils.getCurrentTime
import com.tcf.tcfspeedtester.utils.getNetwork
import com.tcf.tcfspeedtester.utils.logI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SpeedTestCallbacks(private val context: Context) : SpeedTestListener {

    var downloadSpeed: Double = 0.0
    var uploadSpeed: Double = 0.0
    var pingTime :Int = 0
    var upload_progress:Int = 0
    var download_progress:Int = 0
    private val database by lazy { AppDB.getFileDatabase(context).appDao() }
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onTestStarted() {
        logI("internet", "onTestStarted() called")
        try
        {
            if (context != null) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            }
            else { logI("internet", "onTestStarted >> Context is null") }
        }
        catch (e: Exception) { logI("internet", "onTestStarted: Exception >> ${e.message}") }
    }

    override fun onFetchServerFailed() {
        logI("internet", "onFetchServerFailed")
        try {
            if (context != null) { }
            else { logI("internet", "onFetchServerFailed >> Context is null") }
        }
        catch (e: Exception) {
            logI("internet", "onFetchServerFailed: Exception: ${e.message}")
        }
    }

    override fun onFindingBestServerStarted() {}

    override fun onPingStarted() {}

    override fun onPingFinished(pingTimeMs: Int, packetLossPercentage: Int) {
        Log.i("internet", "Ping Time: $pingTime ms, Jitter: $packetLossPercentage ms")
        pingTime = pingTimeMs
    }

    override fun onDownloadTestStarted() {}

    override fun onDownloadTestProgress(downloadProgress: Int, downloadSpeedMbps: Double, uploadSpeedMbps: Double) {
        Log.i("internet", "Download Progress: $downloadProgress%, Speed: $downloadSpeedMbps Mbps")
        downloadSpeed = downloadSpeedMbps
        download_progress = downloadProgress
    }

    override fun onDownloadTestFinished(downloadSpeedMbps: Double) {
        Log.i("internet", "Download Finished. Speed: $downloadSpeedMbps Mbps")
        downloadSpeed = downloadSpeedMbps
    }

    override fun onUploadTestStarted() {}

    override fun onUploadTestProgress(uploadProgress: Int, downloadSpeedMbps: Double, uploadSpeedMbps: Double) {
        Log.i("internet", "Upload Progress: $uploadProgress%,Upload Speed: $uploadSpeedMbps Mbps")
        uploadSpeed = uploadSpeedMbps
        upload_progress = uploadProgress
    }

    override fun onUploadTestFinished(uploadSpeedMbps: Double) {
        Log.i("internet", "Upload Finished. Speed >> $uploadSpeedMbps Mbps")
        uploadSpeed = uploadSpeedMbps
//        (context as LoginActivity).getAllDetails(uploadSpeed,downloadSpeed,upload_progress,download_progress)


        if(upload_progress == 100 && download_progress == 100){

            logI("internet","log >> upload_progress == 100 && download_progress == 100")

        }
        
    }

    override fun onTestFinished(p0: SpeedTestResult?) {
        try{
            SpeedcheckerSDK.SpeedTest.setOnSpeedTestListener(null)
            logI("internet","onTestFinished() ")
        }catch (e:Exception){
            logI("","${e.printStackTrace()}")
        }
        uploadInternetSpeedStatus()
    }

    override fun onTestWarning(message: String?) {
        logI("internet","onTestWarning >> ${Exception().message}")
        logI("internet","onTestWarning() message >> $message")
    }

    override fun onTestFatalError(message: String?) {
        logI("internet","onTestFatalError() >> Exception : ${Exception().message}")
        logI("internet","onTestFatalError message >> $message")
    }

    override fun onTestInterrupted(message: String?) {
        logI("internet","onTestInterrupted message >> $message")
        logI("internet","onTestInterrupted(): Exception >> ${Exception().message}")
    }

    private fun uploadInternetSpeedStatus()
    {
        if (database != null){
            scope.launch {
                var downSpeed = downloadSpeed
                var upSpeed = uploadSpeed
                var time = getCurrentTime()
                var network = context.getNetwork()

                fusedLocationClient.getCurrentLocation(context, onSuccess = { location->
                    var lat = location.latitude
                    var long = location.longitude

                    logI("internet","log >> lat = $lat, long = $long, downSpeed = $downSpeed" +
                            ",upSpeed = $upSpeed, time = $time, network = $network")

                    val model = NetworkModel(lat,long,downSpeed,upSpeed,time,network)
                    database.insertData(model)

                }, onFailure = {
                    val model = NetworkModel(0.0,0.0,downSpeed,upSpeed,time,network)
                    database.insertData(model)
                })
            }
        }
    }


}