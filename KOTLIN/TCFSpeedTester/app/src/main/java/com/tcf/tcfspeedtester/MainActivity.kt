package com.tcf.tcfspeedtester

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.tcf.tcfspeedtester.data.database.client.AppDB
import com.tcf.tcfspeedtester.presentation.LoginActivity
import com.tcf.tcfspeedtester.presentation.SpeedTestActivity
import com.tcf.tcfspeedtester.utils.LocalPreferences
import com.tcf.tcfspeedtester.utils.LocalPreferences.KeyValue.SpeedTest
import com.tcf.tcfspeedtester.utils.LocalPreferences.KeyValue.isLogin
import com.tcf.tcfspeedtester.utils.hideActionBarAndFullScreen
import com.tcf.tcfspeedtester.utils.toast

class MainActivity : AppCompatActivity() {

    private val preferences = LocalPreferences
    private val database by lazy { AppDB.getFileDatabase(this).appDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        hideActionBarAndFullScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(database != null){
            var school = database.getSchoolById()
            if (school != null) { // Check if school is not null
                var isUserLoggedIn = school.isLogin // Corrected method invocation
                if (isUserLoggedIn){
                    Handler().postDelayed({
                        val intent = Intent(this, SpeedTestActivity::class.java)
                        startActivity(intent)
                        finish()
                    },3000)
                }
        } else {
            toast("Preferences not saved.\nPlease login again")
                Handler().postDelayed({
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                },3000)
        }
    }
}
}