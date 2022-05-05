package com.example.sensory

import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lightSensorManager: SensorManager
    private var brightness: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()
        setUpSensor()
    }

    private fun showLocation(latitude: Double, longitude: Double){
        val tvLocation = findViewById<TextView>(R.id.tv_location)

        tvLocation.text = "Twoja lokalizacja: $latitude $longitude"
    }

    private fun showBrightness(brightness: Float){
        val pbLight = findViewById<ProgressBar>(R.id.pb_light) as ProgressBar
        val tvLightVal = findViewById<TextView>(R.id.tv_light_val)
        pbLight.max = 1000
        val progress = kotlin.math.min(brightness.toInt(), 1000)

        pbLight.progress = progress
        tvLightVal.text = brightness.toString()
    }

    private fun setUpSensor(){
        lightSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        brightness = lightSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
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
                showLocation(it.latitude, it.longitude)
            } else {
                Log.d("Location", "location null")
            }
        }

        task.addOnFailureListener { exception ->
            Log.d("Location", "Failure: $exception")
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        Log.d("Sensors", "something changed")
        if (event?.sensor?.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            TODO()
        }

        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            Toast.makeText(this, "Accelometer", Toast.LENGTH_SHORT).show()
        }

        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            showBrightness(event.values[0])
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onResume(){
        super.onResume()
        lightSensorManager.registerListener(this, brightness, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause(){
        super.onPause()
        lightSensorManager.unregisterListener(this)
    }
}
