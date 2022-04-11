package com.example.intelligent_guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intelligent_guess.RankingAdapter

private lateinit var rankingAdapter: RankingAdapter

class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        rankingAdapter = RankingAdapter(mutableListOf())

        val messageList = findViewById<RecyclerView>(R.id.rv_ranking)
        messageList.adapter = rankingAdapter
        messageList.layoutManager = LinearLayoutManager(this)

        val dbHelper = DBHelper(this)
        val users: ArrayList<User> = dbHelper.getUsers(10)

        for(user in users) {
            rankingAdapter.addUser(user)
        }
    }
}