package com.tcf.tcfspeedtester.app

import android.app.Application
import android.content.Intent
import com.tcf.tcfspeedtester.utils.error_handle.ErrorActivity
import kotlin.system.exitProcess

class TCFAppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            handleUncaughtException(throwable)
        }
    }

    private fun handleUncaughtException(throwable: Throwable) {

        val intent = ErrorActivity.newIntent(this, throwable)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        exitProcess(1)
    }
}