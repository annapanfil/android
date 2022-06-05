package com.example.bike_app.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.bike_app.R

class StopperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopper)
        Log.d("debug", "stoper activity")
        val frag = supportFragmentManager.findFragmentById(R.id.f_stopper) as StoperFagment
        val routeId = intent.getLongExtra("route_id", -1)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        frag.setTrackId(routeId)
    }

}