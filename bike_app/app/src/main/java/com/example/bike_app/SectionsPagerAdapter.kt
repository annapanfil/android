package com.example.bike_app;
import android.content.res.Resources
import android.provider.Settings.Global.getString
import androidx.core.content.res.TypedArrayUtils.getText
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
        return TrackListFragment() //TODO: coś sensownego
    }

    override fun getPageTitle(position: Int): String{
        when(position){
            0 -> return "a" //Resources.getSystem().getText(R.string.home_tab) as String
            1 -> return  "b" //Resources.getSystem().getText(R.string.kat1_tab) as String
            2 -> return "c" //Resources.getSystem().getText(R.string.kat2_tab) as String
        }
        return ""
    }
}
