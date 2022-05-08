package com.example.bike_app

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment

class TrackListFragment : ListFragment() {
    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dbHelper = DBHelper(inflater.context)
        val names = dbHelper.getNames()
        val adapter =
            ArrayAdapter<String>(inflater.context, android.R.layout.simple_list_item_1, names)
        listAdapter = adapter

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        listener?.itemClicked(id)
    }

}
