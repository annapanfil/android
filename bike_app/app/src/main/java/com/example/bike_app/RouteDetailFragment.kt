package com.example.bike_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

class RouteDetailFragment : Fragment() {
    private var routeId: Long? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_route_detail, container, false)
    }

    override fun onStart() {
        super.onStart()
        val rdView = view
        if (rdView != null){
            val tvName = rdView.findViewById<TextView>(R.id.tv_name)
            val tvDetails = rdView.findViewById<TextView>(R.id.tv_details)
            val dbHelper = DBHelper(rdView.context)
            tvName.text = dbHelper.getName(routeId as Long)
            tvDetails.text = dbHelper.getDetails(routeId as Long)
        }

    }

    fun setRouteId(id: Long){
        Toast.makeText(context, "id in RouteDetailFrag $id -", Toast.LENGTH_SHORT).show()

        routeId = id;
        Toast.makeText(context, "id in RouteDetailFrag $id $routeId", Toast.LENGTH_SHORT).show()

    }
}