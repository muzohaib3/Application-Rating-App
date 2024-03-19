package com.tcf.tcfspeedtester.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.SupplicantState
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.FusedLocationProviderClient
import java.text.SimpleDateFormat
import java.util.*


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun <T : AppCompatActivity> AppCompatActivity.gotoActivity(targetActivityClass: Class<T>) {
    val intent = Intent(this, targetActivityClass)
    startActivity(intent)
}

fun logI(tag:String, data:String){
    Log.i(tag,data)
}

fun getCurrentTime(): String {
    val dateFormat = SimpleDateFormat("HH:mm:ss")
    val currentTime = Calendar.getInstance().time
    return dateFormat.format(currentTime)
}

fun AppCompatActivity.hideActionBarAndFullScreen() {
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
    supportActionBar?.hide()
}

fun <T> Context.isServiceRunning(service: Class<T>): Boolean {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val services = manager.runningAppProcesses

    if (services != null) {
        for (serviceInfo in services) {
            if (serviceInfo.processName == service.name) {
                return true
            }
        }
    }
    return false
}

//fun FusedLocationProviderClient.getAddressFromLocation(
//    context: Context,
//    onSuccess: (Address) -> Unit,
//    onFailure: () -> Unit
//) {
//    getCurrentLocation(
//        context,
//        onSuccess = { location ->
//            val geocoder = Geocoder(context, Locale.getDefault())
//            val addresses: List<Address> =
//                geocoder.getFromLocation(location.latitude, location.longitude, 1) as List<Address>
//            if (addresses.isNotEmpty()) {
//                onSuccess.invoke(addresses[0])
//            } else {
//                onFailure.invoke()
//            }
//        },
//        onFailure = onFailure
//    )
//}

@SuppressLint("MissingPermission")
fun FusedLocationProviderClient.getCurrentLocation(
    context: Context,
    onSuccess: (Location) -> Unit,
    onFailure: () -> Unit
) {
    if (checkPermissions(context) && isLocationEnabled(context)) {
        this.lastLocation.addOnCompleteListener { task ->
            try {
                val location: Location? = task.result
                if (location != null) {
                    onSuccess.invoke(location)
                } else {
                    onFailure.invoke()
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
                onFailure.invoke()
            }
        }
    } else {
        Toast.makeText(
            context,
            "Please turn on location and grant necessary permissions",
            Toast.LENGTH_LONG
        ).show()
        onFailure.invoke()
    }
}

private fun checkPermissions(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}

private fun isLocationEnabled(context: Context): Boolean {
    val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

fun Context.getNetwork(): String {
    var ssid = ""
    val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
    val wifiInfo: WifiInfo = wifiManager.connectionInfo

    if (wifiInfo.supplicantState == SupplicantState.COMPLETED) {
        ssid = wifiInfo.ssid
    }

    return ssid
}

fun Context.startAppService(context: Context, service: Class<*>){
    val intent = Intent(context, service)
    context.startService(intent)
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        return networkInfo.isConnected && (networkInfo.type == ConnectivityManager.TYPE_WIFI || networkInfo.type == ConnectivityManager.TYPE_MOBILE)
    }
}

fun <T : AppCompatActivity> AppCompatActivity.startActivityWithData(key:String,targetActivityClass: Class<T>,data:Int) {
    val intent = Intent(this, targetActivityClass)
    intent.putExtra(key,data)
    startActivity(intent)
}