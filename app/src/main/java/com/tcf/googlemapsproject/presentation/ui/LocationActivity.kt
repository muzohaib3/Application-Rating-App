package com.tcf.googlemapsproject.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.tcf.googlemapsproject.R
import com.tcf.googlemapsproject.click
import com.tcf.googlemapsproject.data.local.AppDB
import com.tcf.googlemapsproject.data.model.AppRating
import com.tcf.googlemapsproject.databinding.ActivityLocationBinding
import com.tcf.googlemapsproject.gone
import com.tcf.googlemapsproject.presentation.ui.fragments.BottomSheetFragment
import com.tcf.googlemapsproject.toast
import com.tcf.googlemapsproject.visible
import java.util.Locale


class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityLocationBinding
    private val PERMISSION_REQUEST_LOCATION = 100
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val db by lazy { AppDB.getFileDatabase(this) }

    private var latitude = 0.0
    private var longitude = 0.0
    private var w_rate = 0.0
    private var y_rate = 0.0
    private var insta_rate = 0.0
    private var fb_rate = 0.0
    private var twitter = 0.0
    private var linkedin = 0.0
    private var guestKey= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        initViews()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLocation()

        try
        {
            if (intent.extras != null) {
                guestKey = intent.extras?.getBoolean("guestMode")!!
                when{
                    guestKey ->
                    {
                        toast(this,"map is in guest mode!!!")
                        println("The value of guest mode is = $guestKey")
                        showRatingMarkers()
                    }
                    else->{

                    }
                }
            }
            else {
                toast(this,"map is not in guest mode!!!")
                Log.i("exception","${Exception().message}")
            }
        }
        catch (e:Exception)
        {
            Log.i("exception","${e.message}")
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation()
    {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location = task.result
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val list: MutableList<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)!!
                    latitude = list[0].latitude
                    longitude = list[0].longitude


                    val latlng = LatLng(latitude, longitude)
                    var markerOption = MarkerOptions().position(latlng).title("Current location")
                    mMap.addMarker(markerOption)
                    Log.i("MapsReview", " location $location, latitude $latitude")


                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_LOCATION
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }


    private fun initViews(){


        binding.btShowRating.click {
            binding.llAppRating.visible()
        }

        binding.btSubmitRating.click {
            binding.llAppRating.gone()
            insertAppDetails()
        }

        binding.ratingBar.rating = 2.5f
        binding.ratingBar.stepSize = .5f
        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            binding.tvWhatsAppRate.text = rating.toString()
            w_rate = rating.toDouble()
        }

        binding.ratingBar2.rating = 2.5f
        binding.ratingBar2.stepSize = .5f
        binding.ratingBar2.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            binding.tvFacebookRate.text = rating.toString()
            fb_rate = rating.toDouble()
        }

        binding.ratingBar3.rating = 2.5f
        binding.ratingBar3.stepSize = .5f
        binding.ratingBar3.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            binding.tvYoutubeRate.text = rating.toString()
            y_rate = rating.toDouble()
        }

        binding.ratingBar4.rating = 2.5f
        binding.ratingBar4.stepSize = .5f
        binding.ratingBar4.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            binding.tvInstaRate.text = rating.toString()
            insta_rate = rating.toDouble()
        }

        binding.ratingBar5.rating = 2.5f
        binding.ratingBar5.stepSize = .5f
        binding.ratingBar5.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            binding.tvTwitterRate.text = rating.toString()
            twitter = rating.toDouble()
        }

        binding.ratingBar6.rating = 2.5f
        binding.ratingBar6.stepSize = .5f
        binding.ratingBar6.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            binding.tvLinkedinRate.text = rating.toString()
            linkedin = rating.toDouble()
        }

    }

    private fun insertAppDetails(){

        val rate = AppRating(latitude,longitude,w_rate,y_rate,linkedin,twitter,fb_rate,insta_rate)
        db.appDao().insertAppData(rate)

    }

    private fun showRatingMarkers(){

        binding.btShowRating.isEnabled = false

        val getAllRatingData =  db.appDao().getAllRating()

        if (getAllRatingData != null && getAllRatingData.isNotEmpty()){

            for(i in getAllRatingData.indices){

                var lat = getAllRatingData[i].latitude
                var long = getAllRatingData[i].longitude

                var whatsApp = getAllRatingData[i].whatsapp_rate
                var youtube = getAllRatingData[i].youtube_rate
                var facebook = getAllRatingData[i].facebook_rate
                var twitter = getAllRatingData[i].twitter_rate
                var linkedin = getAllRatingData[i].linkedin_rate
                var instagram = getAllRatingData[i].insta_rate

                var rate_array = arrayListOf<Double>()
                rate_array.add(0,whatsApp)
                rate_array.add(1,youtube)
                rate_array.add(2,facebook)
                rate_array.add(3,twitter)
                rate_array.add(4,linkedin)
                rate_array.add(5,instagram)

                Log.i("app_rate","rate_array = $rate_array and withIndex ${rate_array.withIndex()}")

                val bm = BitmapFactory.decodeResource(resources, R.drawable.star)
                val resizedBitmap = Bitmap.createScaledBitmap(bm, 50, 50, false)
                createMarker(lat,long,resizedBitmap)

            }

            mMap.setOnMarkerClickListener { marker ->
                updateBottomSheetContent()
                true
            }

        }else{

        }
    }

    fun createMarker(latitude: Double, longitude: Double, image: Bitmap): Marker? {
        return mMap.addMarker(
            MarkerOptions()
                .position(LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromBitmap(image))
        )
    }

    private fun updateBottomSheetContent(){

        val bottomSheet = BottomSheetFragment()
        bottomSheet.show(supportFragmentManager, "ModalBottomSheet")
    }

}



