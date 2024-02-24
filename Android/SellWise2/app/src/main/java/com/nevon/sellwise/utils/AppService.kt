package com.nevon.sellwise.utils

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class AppService:Service() {

    override fun onCreate() {
        super.onCreate()
        Log.i("service","BackgroundTaskService is ready to conquer!")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("service","The service onStartCommand")

        try {
            Log.i("service","Service message")
            Thread.sleep(2000)
        }catch (e:Exception){
            Log.i("service","")
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("service","The service onDestroy")
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}