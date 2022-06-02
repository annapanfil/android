package com.example.bike_app.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bike_app.*


class ShortTrackListFragment : Fragment() {
    private var listener: Listener? = null

    fun String.normalize(): String {
        //source: http://tkadziolka.pl/blog/usuwanie_polskich_znakow.html
        val original = arrayOf("Ą", "ą", "Ć", "ć", "Ę", "ę", "Ł", "ł", "Ń", "ń", "Ó", "ó", "Ś", "ś", "Ź", "ź", "Ż", "ż")
        val normalized = arrayOf("A", "a", "C", "c", "E", "e", "L", "l", "N", "n", "O", "o", "S", "s", "Z", "z", "Z", "z")

        return this.map { char ->
            val index = original.indexOf(char.toString())
            if (index >= 0) normalized[index] else char
        }.joinToString("")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dbHelper = DBHelper(inflater.context)
        val names = dbHelper.getNames("short")
        val images =  IntArray(Route.routes.size)
        Log.d("debug", "dynamic")
        for (i in names.indices){
            val name = names[i].lowercase().replace(" ", "_").normalize()
            images[i] = resources.getIdentifier(name, "drawable", context?.packageName)
        }

        val routeRecycler: RecyclerView = inflater.inflate(R.layout.short_track_list, container, false) as RecyclerView

        val adapter = CaptionedImagesAdapter(names, images)
        routeRecycler.adapter = adapter
        val layoutManager = GridLayoutManager(activity, 2)
        routeRecycler.layoutManager = layoutManager

        return routeRecycler
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

//    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
//        listener?.itemClicked(id)
//    }

}
