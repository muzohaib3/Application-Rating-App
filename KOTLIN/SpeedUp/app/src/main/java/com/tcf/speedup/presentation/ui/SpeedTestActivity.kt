package com.tcf.speedup.presentation.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.tcf.speedup.data.local.AppDB
import com.tcf.speedup.data.models.NetworkModel
import com.tcf.speedup.databinding.ActivitySpeedTestBinding
import com.tcf.speedup.utils.gone
import com.tcf.speedup.utils.isNetworkConnected
import com.tcf.speedup.utils.visible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


class SpeedTestActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySpeedTestBinding
    private val db by lazy { AppDB.getFileDatabase(this) }
    private val scope = CoroutineScope(Dispatchers.IO)
    private val mainHandler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpeedTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

//        mainHandler.postDelayed({
//            insertNetworkDetails(upSpeed, downSpeed)
//            handlerLatency(this,upSpeed, downSpeed)
//        }, 3600000) // 3600 seconds = 1 hour
    }

    private fun initViews(){

        getInternetSpeed(this)

        binding.btSubmit.setOnClickListener {
            credentials()
        }

    }

    private fun credentials(){

        var schoolId = binding.etSchoolId.text.toString()
        var schoolName = binding.etSchoolName.text.toString()

        when{
            schoolId.isNullOrEmpty()->{
                Toast.makeText(this, "schoolId not found", Toast.LENGTH_SHORT).show()
            }
            schoolName.isNullOrEmpty()->{
                Toast.makeText(this, "schoolName not found", Toast.LENGTH_SHORT).show()
            }
            else->{
                Log.i("internet","schoolId : $schoolId schoolName: $schoolName")
                Toast.makeText(this, "data submitted", Toast.LENGTH_SHORT).show()
                binding.llSchoolDetails.gone()
                binding.llSchoolDetails.animate().alpha(0f).duration = 200

                binding.llShareOption.visible()

                binding.llShareOption.animate().alpha(1f).duration = 200
            }
        }

    }





    private fun handlerLatency(context: Context,upSpeed:Int, downSpeed:Int){

        val mainHandler = Handler(Looper.getMainLooper())
        val delayMillis = (3600 * 1000).toLong()

        mainHandler.postDelayed({

            if (isNetworkConnected(context)) {
                insertNetworkDetails(upSpeed, downSpeed)
                println("Down Speed: \$downSpeedMbps Mbps")
                println("Up Speed: \$upSpeedMbps Mbps")
            }
            else {
                Toast.makeText(this, "Internet not found", Toast.LENGTH_SHORT).show()
            }

        }, delayMillis)
    }

    private fun textFile(fileNameString:String, fileContents:String){

        // File create and write
        val fileName = "$fileNameString.txt"
        val fileContents = fileContents

        try {
            val fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            fileOutputStream.write(fileContents.toByteArray())
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



    /** internet code **/

    private fun getInternetSpeed(context: Context){

        try {
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo

            val nc = cm.getNetworkCapabilities(cm.activeNetwork)
            val downSpeed = nc!!.linkDownstreamBandwidthKbps
            val upSpeed = nc.linkUpstreamBandwidthKbps

            Log.i("internet","activeNetwork >> $netInfo, downSpeed >> $downSpeed , upSpeed >> $upSpeed")
            println("Down Speed: $downSpeed kbps")
            println("Up Speed: $upSpeed kbps")

            val downSpeedMbps = downSpeed / 1024.0
            val upSpeedMbps = upSpeed / 1024.0

            binding.ivInternetOn.setOnClickListener {

                if (isNetworkConnected(this)) {

                    insertNetworkDetails(upSpeed, downSpeed)
                    println("Down Speed: $downSpeedMbps Mbps")
                    println("Up Speed: $upSpeedMbps Mbps")

                    var fileContent = "uploading speed $upSpeedMbps Mbps \n" +
                            "downloading speed $downSpeedMbps Mbps " +
                            "\nlatitude = 12.08 " +
                            "\nlng = 16.07 "
                    scope.launch {
                        textFile("networkSpeed",fileContent)
                    }
                }
                else {
                    binding.ivInternetOn.isEnabled = false
                    Toast.makeText(this, "Internet not found", Toast.LENGTH_SHORT).show()
                }
            }
        }catch (e:Exception){
            Log.i("internet","activeNetwork >> ${e.message}")
        }

    }

    private fun insertNetworkDetails(upSpeed:Int, downSpeed:Int){

        var data = NetworkModel(24.0,16.0,upSpeed,downSpeed )
        scope.launch {
            db.appDao().insertAppRecords(data)
        }
    }

}