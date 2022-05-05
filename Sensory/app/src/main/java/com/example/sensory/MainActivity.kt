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
//    private lateinit var locationRequest: LocationRequest
//    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()
//        startLocationUpdates();
//        getLocationUpdates();
    }

    private fun showlocation(latitude: Double, longitude: Double){
        val tvLocation = findViewById<TextView>(R.id.tv_location)

        tvLocation.text = "Twoja lokalizacja: ${latitude} ${longitude}"

        Log.d("Location", "location ${latitude} ${longitude}")
        Toast.makeText(this, "${latitude} ${longitude}", Toast.LENGTH_SHORT).show()

    }

    private fun fetchLocation(){
        Log.d("Location", "in fetchLocation")
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
        }

        val task = fusedLocationClient.lastLocation
        task.addOnSuccessListener {
            Log.d("Location", "Success")
            if(it != null){
                showlocation(it.latitude, it.longitude)
            }
            else{
                Log.d("Location", "location null")
            }
        }

        task.addOnFailureListener { exception ->
            Log.d("Location", "Failure: ${exception}")
        }

        Log.d("Location", "end")
    }

//    private fun getLocationUpdates()
//    {
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        locationRequest = LocationRequest()
//        locationRequest.interval = 50000
//        locationRequest.fastestInterval = 50000
//        locationRequest.smallestDisplacement = 170f // 170 m = 0.1 mile
//        locationRequest.priority = LocationRequest.PRIORITY_LOW_POWER //set according to your app function
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult?) {
//                locationResult ?: return
//
//                if (locationResult.locations.isNotEmpty()) {
//                    // get latest location
//                    val location =
//                        locationResult.lastLocation
//                    Log.d("LOCATION", location.latitude.toString())
//                    Log.d("LOCATION", location.longitude.toString())
//                    // use your location object
//                    // get latitude , longitude and other info from this
//                }
//
//
//            }
//        }
//    }
//
////    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: Array<Int>){
////
////    }
//    // to handle the case where the user grants the permission. See the documentation
//    // for ActivityCompat#requestPermissions for more details.
//
//    //start location updates
//    private fun startLocationUpdates() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            var permissions = arrayOf("s", "a")
//            ActivityCompat.requestPermissions(this, arrayOf(" ACCESS_COARSE_LOCATION", "ACCESS_FINE_LOCATION"), 1);
//            return
//        }
//        fusedLocationClient.requestLocationUpdates(
//            locationRequest,
//            locationCallback,
//            null /* Looper */
//        )
//    }
//
//    // stop location updates
//    private fun stopLocationUpdates() {
//        fusedLocationClient.removeLocationUpdates(locationCallback)
//    }
//
//    // stop receiving location update when activity not visible/foreground
//    override fun onPause() {
//        super.onPause()
//        stopLocationUpdates()
//    }
//
//    // start receiving location update when activity  visible/foreground
//    override fun onResume() {
//        super.onResume()
//        startLocationUpdates()
//    }



}
