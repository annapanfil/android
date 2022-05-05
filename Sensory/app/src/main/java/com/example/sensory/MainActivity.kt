package com.example.sensory

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.*

class MainActivity : AppCompatActivity(), SensorEventListener{
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var sensorManager: SensorManager
    private lateinit var receiver: BroadcastReceiver
    private lateinit var batteryFilter: IntentFilter
    private var brightness: Sensor? = null
    private var temperature: Sensor? = null
    private var accelerometer: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()
        setUpSensor()

        val thread = Thread(){
            while(true){
                run{
                    Thread.sleep(60000)
                }
                runOnUiThread(){
                    registerReceiver(receiver, batteryFilter)
                }
            }
        }
        thread.start()
    }

    // SHOWING
    private fun showLocation(latitude: Double, longitude: Double){
        val tvLocation = findViewById<TextView>(R.id.tv_location)

        tvLocation.text = "Twoja lokalizacja: $latitude $longitude"
    }

    private fun showBrightness(brightness: Float){
        // on the progress bar and in the text view
        val pbLight = findViewById<ProgressBar>(R.id.pb_light) as ProgressBar
        val tvLightVal = findViewById<TextView>(R.id.tv_light_val)
        pbLight.max = 1000
        val progress = kotlin.math.min(brightness.toInt(), 1000)

        pbLight.progress = progress
        tvLightVal.text = brightness.toString()
    }

    private fun changeSquare(sides: Float, updown: Float){
        val square = findViewById<TextView>(R.id.tv_square)
        square.apply{
            rotationX = updown * 3f
            rotationY = sides * 3f
            rotation = -sides
            translationX = sides * -10
            translationY = updown * 10
        }
        square.text = "up/down ${updown.toInt()}\nleft/right ${sides.toInt()}"
    }

    private fun showTemperature(temperature: Float){
        Toast.makeText(this, "Battery Temperature: $temperature${0x00B0.toChar()}C", Toast.LENGTH_SHORT).show()
    }

    // Configuration
    private fun setUpSensor() {
        // start the light sensor
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER).also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_FASTEST)
        batteryFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        }

        // initialize a new broadcast receiver instance
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // get battery temperature programmatically from intent
                intent?.apply {
                    val temp = getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10F
                    showTemperature(temp)
                }
            }
        }
    }

    private fun fetchLocation() {
        // check permissions
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

        // get last location
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
            showTemperature(event.values[0])
        }

        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            changeSquare(event.values[0], event.values[1])
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
        sensorManager.registerListener(this, brightness, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_FASTEST)

    }

    override fun onPause(){
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}
