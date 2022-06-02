package com.example.bike_app.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridLayout
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bike_app.*


class ShortTrackListFragment : Fragment() {
    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val dbHelper = DBHelper(inflater.context)
//        val names = dbHelper.getNames("short")
        val routeRecycler: RecyclerView = inflater.inflate(R.layout.short_track_list, container, false) as RecyclerView

        val routesNames = arrayOfNulls<String>(Route.routes.size)
        val routesImages = IntArray(Route.routes.size)
        for (i in routesNames.indices) {
            routesNames[i] = Route.routes[i].name
            routesImages[i] = Route.routes[i].imgResourceId
        }

        val adapter = CaptionedImagesAdapter(routesNames, routesImages)
        routeRecycler.adapter = adapter
        val layoutManager = GridLayoutManager(activity, 2)
        routeRecycler.layoutManager = layoutManager

        return routeRecycler


//        val adapter =
//            ArrayAdapter<String>(inflater.context, android.R.layout.simple_list_item_1, names)
//        listAdapter = adapter

//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

//    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
//        listener?.itemClicked(id)
//    }

}
