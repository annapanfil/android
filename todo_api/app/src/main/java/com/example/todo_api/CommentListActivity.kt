package com.example.todo_api

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intelligent_guess.DBHelper


private lateinit var commentAdapter: CommentAdapter

class CommentListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        val postId = intent.getIntExtra("postId", -1)
        val name = intent.getStringExtra("name")

        Log.d("DEBUG", "postid " + postId)

        val tvTitle = findViewById<TextView>(R.id.tv_posttitle)
        val commentList = findViewById<RecyclerView>(R.id.rv_comments)
        val bPrevious = findViewById<ImageButton>(R.id.b_previous)
        val bHome = findViewById<ImageButton>(R.id.b_home)

        tvTitle.text = name

        // list
        commentAdapter = CommentAdapter(mutableListOf())

        commentList.adapter = commentAdapter
        commentList.layoutManager = LinearLayoutManager(this)

        val dbHelper = DBHelper(this)
        val commentArray = dbHelper.getComments(postId)

        for(comment in commentArray) {
            commentAdapter.addComment(comment)
        }

        // listeners
        bPrevious.setOnClickListener(){
            setResult(RESULT_CANCELED, null);
            finish()
        }

        bHome.setOnClickListener()
        {
            val openMainActivity = Intent(this, MainActivity::class.java)
            openMainActivity.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivityIfNeeded(openMainActivity, 0)
            setResult(RESULT_OK, null);
            finish()
        }


    }
}