package com.example.bike_app.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bike_app.*


class TrackListFragment (val type: String="") : Fragment() {
    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dbHelper = DBHelper(inflater.context)
        val routes = dbHelper.getNames(type)
        val names = arrayOfNulls<String>(routes.size)
        val images =  IntArray(routes.size)
        for (i in routes.indices){
            names[i] = routes[i].name
            val name = names[i]!!.lowercase().replace(" ", "_").normalize()
            images[i] = resources.getIdentifier(name, "drawable", context?.packageName)
        }

        val routeRecycler: RecyclerView = inflater.inflate(R.layout.short_track_list, container, false) as RecyclerView

        val adapter = CaptionedImagesAdapter(names, images)
        routeRecycler.adapter = adapter
        val layoutManager = GridLayoutManager(activity, 2)
        routeRecycler.layoutManager = layoutManager

        //onclick
        adapter.listener = object : CaptionedImagesAdapter.Listener {
            override fun onClick(position: Int) {
                Log.d("debug", "click $position")
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("route_id", routes[position].id)
                activity!!.startActivity(intent)
            }
        }

        return routeRecycler
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        listener = context as Listener
//    }
//
//    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
//        listener?.itemClicked(id)
//    }

}