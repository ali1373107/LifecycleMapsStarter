package com.example.lifecyclemaps

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle

class MainActivity : AppCompatActivity() {
    val viewModel: LocationViewModel by viewModels()
    lateinit var observer: LocationLifecycleObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()

        val observe = LocationLifecycleObserver(this,viewModel) //** why we need to use here this parameter but in the previuse exesise we didnt use it in lifecycle tasks
        this.lifecycle.addObserver(observe)


        //viewModel.LatLonLiveData.observe(this,{

       // })//***how to update the view model observer ***regarding to last week exercises
    // You need to create the lifecycle observer and add it to the lifecycle
    }

    fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        } else {
            // Set "gpsPermission" to true in the lifecycle observer
            observer.gpsPermission = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            // Set "gpsPermission" to true in the lifecycle observer
            observer.gpsPermission = true
        } else {
            AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage("GPS permission denied").show()
            // Set "gpsPermission" to false in the lifecycle observer
            observer.gpsPermission = false
        }
    }
}

