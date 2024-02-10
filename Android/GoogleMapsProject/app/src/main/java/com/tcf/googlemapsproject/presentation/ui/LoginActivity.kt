package com.tcf.googlemapsproject.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tcf.googlemapsproject.click
import com.tcf.googlemapsproject.data.local.AppDB
import com.tcf.googlemapsproject.data.model.User
import com.tcf.googlemapsproject.databinding.ActivityLoginBinding
import com.tcf.googlemapsproject.gone
import com.tcf.googlemapsproject.gotoActivity
import com.tcf.googlemapsproject.toast
import com.tcf.googlemapsproject.visible

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private val db by lazy { AppDB.getFileDatabase(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews(){

        binding.btLogin.setOnClickListener {
            var email = binding.etEmail.text.toString().trim()
            var password = binding.etPassword.text.toString().trim()
            loginUser(email,password)
        }

        binding.tvSignup.setOnClickListener {
            binding.llLogin.gone()
            binding.llSignup.root.visible()
        }

        binding.llSignup.btSignup.click {

            var phNo = binding.llSignup.etSignupPhNo.text.toString().trim()
            var email = binding.llSignup.etSignupEmail.text.toString().trim()
            var password = binding.llSignup.etSignupPassword.text.toString().trim()
            var fullname = binding.llSignup.etSignupUName.text.toString().trim()
            signupUser(email,password,phNo,fullname)

        }

        binding.btGuest.click {
            val intent = Intent(this, LocationActivity::class.java)
            intent.putExtra("guestMode",true)
            startActivity(intent)
        }

    }

    private fun loginUser(email:String,password:String){

         when{
            email.isEmpty() and password.isEmpty()->{
                toast(this,"email is empty")
            }

            email.isNullOrEmpty() or password.isNullOrEmpty()->{
                toast(this,"password is empty")
            }

            else->{

                val user = db.appDao().getEmailPassword(email)
                if (user != null){
                    var dbEmail = user.email
                    var dbPassword = user.password

                    println("dbEmail = $dbEmail , dbPassword = $dbPassword ,email = $email , password = $password")

                    if ((dbEmail == email) and (dbPassword == password )){
                        toast(this,"login successfull")
                        gotoActivity(this, LocationActivity::class.java)
                    }
                    else{
                        toast(this, "email or password maybe incorrect")
                    }
                } else{
                    toast(this, "database value not found")
                }
            }
        }
    }

    private fun signupUser(email:String,password:String,phNo:String,name:String){

        when{
            email.isNullOrEmpty()->{
                toast(this,"email is empty")
            }
            password.isNullOrEmpty()->{
                toast(this,"password is empty")
            }
            phNo.isNullOrEmpty()->{
                toast(this,"phNo is empty")
            }
            name.isNullOrEmpty()->{
                toast(this,"name is empty")
            }
            else->{
                toast(this,"User signup successfully")
                val user = User(name, email, password, phNo)

                if (user != null){
                    db.appDao().insert(user)
                    binding.llSignup.root.gone()
                    binding.llLogin.visible()
                }else{
                    toast(this,"database is null")
                }
            }
        }

    }


}