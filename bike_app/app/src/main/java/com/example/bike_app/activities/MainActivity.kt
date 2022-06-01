package com.example.bike_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.bike_app.activities.DetailActivity
import com.example.bike_app.activities.RouteDetailFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), Listener {
    fun addTracksData(){
        //source: https://regionwielkopolska.pl/artykuly-turystyka/szlaki-w-okolicach-poznania-2/
        val dbhelper = DBHelper(this)
        dbhelper.update()
        dbhelper.insertTrack("Pierścień rowerowy dookoła Poznania",
            "Pierścień tworzy zamkniętą pętlę – nie ma początku ani końca. Wiedzie trasą o trudności zróżnicowanej w ciekawym terenie. Siedem szlaków łącznikowych (niezbyt forsownych) to propozycja dla tych, którzy wolą zwiedzanie i wypoczynek od sportowych emocji. Oznakowanie: pomarańczowe kwadraty 20/20 cm, piktogram roweru, pod nim czarny znak kierunkowy\n",
            "Mosina – Pożegowo – Górka – Stęszew – Krąplewo – Mirosławki – Tomice – Podłoziny – Dopiewo – Zborowo – Drwęca – Lusówko – Lusowo – Sady – Kiekrz – Złotniki – Biedrusko – Promnice – Raduszyn – Murowana Goślina – Rakownia – Floryda – Huciska – Zielonka – Dąbrówka Kościelna – Stęszew – Bednary – Wronczyn – Borowo – Promno – Góra – Tarnowo – Kostrzyn – Ignacewo – Mały Trzek – Trzek Duży – Gowarzewo – Tulce – Robakowo – Borówiec – Kórnik – Bnin – Konarskie – Radzewo – Radzewice – Świątniki – Rogalin – Rogalinek – Mosina",
            "long"
        )
        dbhelper.insertTrack("Cysterski Szlak Rowerowy",
            "Szlak cysterski rozpoczyna się przy kościele pw. św. Jana Jerozolimskiego przy Rondzie Śródka w Poznaniu. Ponieważ jego trasa wiedzie zarówno przez okolice Poznania jak i  północną Wielkopolskę, opis trasy znajdziemy również w szlakach Wielkopolski północnej. Oznakowanie: na białym tle, stylizowany czarny krzyż",
            "Poznań Śródka - Swarzędz - Dziewicza Góra - Owińska - Zielonka - Dąbrówka Kościelna - Rejowiec - Skoki - Rościnno - Wiatrowo - Wągrowiec - Tarnowo Pałuckie - Łekno - Bracholin - Mieścisko - Budziejewko - Popowo Kościelne - Raczków - Bliżyce - Dąbrówka Kościelna",
            "short"
        )
        dbhelper.insertTrack("Doliną Cybiny do Parku Krajobrazowego Promno",
            "Oznakowanie: szlak czarny",
        "Poznań (róg ul. Jana Pawła II i Abpa Baraniaka) – PKP Poznań Antoninek – Uzarzewo – Biskupice – Park Krajobrazowy Promno",
        "short"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addTracksData()
        setContentView(R.layout.activity_main)
        val pagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        val pager = findViewById<ViewPager>(R.id.pager)
        pager.adapter = pagerAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(pager)
    }

    override fun itemClicked(trackId: Long) {
        val flDetails = findViewById<FrameLayout>(R.id.fl_detail)
        Toast.makeText(this, "id $trackId", Toast.LENGTH_SHORT).show()
        if (flDetails != null){
            Log.d("URZDZENIE", "tu tablet")
            val details = RouteDetailFragment()
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            Log.d("DEBUG", "ID $trackId")

            details.setTrackId(trackId+1)
            ft.replace(R.id.fl_detail, details)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.addToBackStack(null)
            ft.commit()
        }
        else {
            Log.d("URZDZENIE", "tu telefon")
            Toast.makeText(this, "Click $trackId", Toast.LENGTH_SHORT).show()
            intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("route_id", trackId + 1)
            startActivity(intent)
        }
    }
}