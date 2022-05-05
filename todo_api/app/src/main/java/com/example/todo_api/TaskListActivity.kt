package com.example.todo_api

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intelligent_guess.DBHelper

private lateinit var todoAdapter: TodoAdapter
private lateinit var postAdapter: PostAdapter

class TaskListActivity : AppCompatActivity() {
    private fun onPostClick(post: Post) {
        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, CommentListActivity::class.java)
        intent.putExtra("postId", post.id)
        intent.putExtra("name", post.title)
        startActivityForResult(intent, 0)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        val userId = intent.getIntExtra("id", -3)
        val userName = intent.getStringExtra("name")

        //show
        val dbHelper = DBHelper(this)
        val todoArray = dbHelper.getTodos(userId)
        val postArray = dbHelper.getPosts(userId)

        setContentView(R.layout.activity_task_list)
        val tvName = findViewById<TextView>(R.id.tv_posttitle)
        tvName.text = userName

        todoAdapter = TodoAdapter(mutableListOf())
        postAdapter = PostAdapter(mutableListOf())

        val todoList = findViewById<RecyclerView>(R.id.rv_comments)
        todoList.adapter = todoAdapter
        todoList.layoutManager = LinearLayoutManager(this)

        for(todo in todoArray) {
            todoAdapter.addTodo(todo)
        }

        val postList = findViewById<RecyclerView>(R.id.rv_posts)
        postList.adapter = postAdapter
        postList.layoutManager = LinearLayoutManager(this)

        for(post in postArray) {
            postAdapter.addPost(post)
        }

        postAdapter.onItemClick = {post -> onPostClick(post)}

        val bPrevious = findViewById<ImageButton>(R.id.b_previous)

        bPrevious.setOnClickListener(){
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            finish()
        }

    }
}