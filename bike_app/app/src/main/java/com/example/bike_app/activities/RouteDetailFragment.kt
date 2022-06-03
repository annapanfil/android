package com.example.bike_app.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.example.bike_app.DBHelper
import com.example.bike_app.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RouteDetailFragment : Fragment() {
    private var routeId: Long? = null
    private var stopper: StoperFagment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null){
//            stopper = StoperFagment()
//            val ft = childFragmentManager.beginTransaction()
//            stopper?.setTrackId(routeId)
//            ft.add(R.id.fl_stoper, stopper!!)
//            ft.addToBackStack(null)
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            ft.commit()
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
            val ivImage = rdView.findViewById<ImageView>(R.id.iv_route_image)
            val fabStats = rdView.findViewById<FloatingActionButton>(R.id.fab_stats)

            val dbHelper = DBHelper(rdView.context)
            val routeName = dbHelper.getName(routeId as Long)
            tvName.text = routeName
            tvDetails.text = dbHelper.getDetails(routeId as Long)

            val nameNormalized = routeName?.lowercase()?.replace(" ", "_")?.normalize()
            val imageId = resources.getIdentifier(nameNormalized, "drawable", context?.packageName)

            ivImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), imageId))
            ivImage.contentDescription = getString(R.string.route_img) + routeName

            fabStats.setOnClickListener(){
                Log.d("debug", "click stats")
                val intent = Intent(activity, StopperActivity::class.java)
                intent.putExtra("route_id", routeId)
                startActivity(intent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("routeId", routeId!!)
    }

    fun setTrackId(trackId: Long){
        Log.d("DEBUG", "id in RouteDetailFrag $trackId $routeId")
        routeId = trackId
        stopper?.setTrackId(routeId)
    }
}