package com.tcf.workpro.presentation.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tcf.workpro.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginUser()
    }

    private fun loginUser(){

       binding.btLogin.setOnClickListener {
           gotoActivity(HomeActivity::class.java)
       }

    }

    private fun gotoActivity(activity:Class<*>){
        val intent = Intent(this, activity)
        startActivity(intent)
    }

}