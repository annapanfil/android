package com.example.bike_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val frag = supportFragmentManager.findFragmentById(R.id.f_details) as RouteDetailFragment
        val routeId = intent.getLongExtra("route_id", -1)
        frag.setRouteId(routeId)
    }
}