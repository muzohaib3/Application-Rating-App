package com.tcf.tcfspeedtester.service

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.speedchecker.android.sdk.SpeedcheckerSDK
import com.tcf.tcfspeedtester.R
import com.tcf.tcfspeedtester.callback.SpeedTestCallbacks
import com.tcf.tcfspeedtester.utils.isNetworkAvailable
import com.tcf.tcfspeedtester.utils.logI
import com.tcf.tcfspeedtester.utils.toast

class TCFService() : Service() {

    private lateinit var speedTestCallbacks: SpeedTestCallbacks
    val handler = Handler()
    private val CHANNEL_ID = "ForegroundServiceChannel"

    override fun onCreate() {
        super.onCreate()
        logI("internet", "Service onCreate called")
        try {
            speedTestCallbacks = SpeedTestCallbacks(this)
            SpeedcheckerSDK.init(this);
//            SpeedcheckerSDK.askPermissions(applicationContext as Activity?)
        } catch (e: Exception) {
            logI("internet", "exception >> ${e.message}")
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logI("internet", "onStartCommand")

        checkSpeedTest()

        createNotificationChannel()

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Service is running...")
            .setSmallIcon(R.drawable.app_logo_final)
            .build()

        startForeground(1, notification)

        if (isNetworkAvailable()) {
            handler.postDelayed(object : Runnable {
                override fun run() {
                    checkSpeedTest()
                    logI("internet", "handler() called")
                    handler.postDelayed(this, 3 * 60 * 1000)
                }
            }, 3 * 60 * 1000)
        } else {
            toast("No connected available. Connect to internet")
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException()
    }

    override fun stopService(name: Intent?): Boolean {
        logI("internet", "Service stopped")
        return super.stopService(name)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun checkSpeedTest() {
        SpeedcheckerSDK.SpeedTest.setOnSpeedTestListener(speedTestCallbacks)
        SpeedcheckerSDK.SpeedTest.startTest(this)
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }


}
