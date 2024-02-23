package com.tcf.speedup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.tcf.speedup.presentation.ui.SpeedTestActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            gotoActivity()
        },3000)
    }

    private fun gotoActivity(){
        val intent = Intent(this, SpeedTestActivity::class.java)
        startActivity(intent)
    }
}