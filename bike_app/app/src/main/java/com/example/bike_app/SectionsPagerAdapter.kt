package com.example.bike_app
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.bike_app.activities.*

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
       return 3
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return MainTabFragment()
            1 -> return TrackListFragment("long")
            2 -> return TrackListFragment("short")
        }
        return TrackListFragment("")
    }

    override fun getPageTitle(position: Int): String{
        when(position){
            0 -> return "Ekran główny"
            1 -> return  "trasy długie" //Resources.getSystem().getText(R.string.kat1_tab) as String
            2 -> return "trasy krótkie" //Resources.getSystem().getText(R.string.kat2_tab) as String
        }
        return ""
    }
}
