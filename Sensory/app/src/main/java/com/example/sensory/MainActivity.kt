package com.example.sensory

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()
    }

    private fun showlocation(latitude: Double, longitude: Double){
        val tvLocation = findViewById<TextView>(R.id.tv_location)

        tvLocation.text = "Twoja lokalizacja: ${latitude} ${longitude}"
    }

    private fun fetchLocation() {
        Log.d("Location", "in fetchLocation")
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }

        val task = fusedLocationClient.lastLocation
        task.addOnSuccessListener {
            Log.d("Location", "Success")
            if (it != null) {
                showlocation(it.latitude, it.longitude)
            } else {
                Log.d("Location", "location null")
            }
        }

        task.addOnFailureListener { exception ->
            Log.d("Location", "Failure: ${exception}")
        }
    }
}
