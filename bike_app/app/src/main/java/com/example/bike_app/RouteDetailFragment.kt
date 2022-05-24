package com.example.bike_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction

class RouteDetailFragment : Fragment() {
    private var routeId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null){
            val stoper = StoperFagment()
            val ft = childFragmentManager.beginTransaction()
            stoper.setTrackId(routeId)
            ft.add(R.id.fl_stoper, stoper)
            ft.addToBackStack(null)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.commit()
        }
        else{
            routeId = savedInstanceState.getLong("routeId")
        }
    }

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("routeId", routeId!!)
    }

    fun setTrackId(trackId: Long){
        Log.d("DEBUG", "id in RouteDetailFrag $trackId $routeId")
        routeId = trackId

    }
}