package com.example.bike_app;
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.ListFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bike_app.activities.StoperFagment
import com.example.bike_app.activities.TrackListFragment

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
       return 3
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return TrackListFragment()
            1 -> return  TrackListFragment()
            2 -> return TrackListFragment()
        }
        return StoperFagment() //TODO: coś sensownego
    }
}
