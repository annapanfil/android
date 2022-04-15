package com.example.todo_api
import android.content.Intent
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.net.URL
import android.util.JsonReader
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intelligent_guess.DBHelper
import java.io.StringReader
import kotlin.reflect.typeOf


private lateinit var userAdapter: UserAdapter

class MainActivity : AppCompatActivity() {
    fun addUsersToDB(usersArray: ArrayList<User>){
        val dbHelper = DBHelper(this)
        for (user in usersArray) {
            dbHelper.insertUser(user.id, user.name, user.email)
        }
    }

    fun addTodosToDB(todosArray: ArrayList<Todo>){
        val dbHelper = DBHelper(this)
        for (todo in todosArray) {
            dbHelper.insertTodo(todo.id, todo.userId, todo.title, todo.completed)
        }
    }

    fun addPostsToDB(postsArray: ArrayList<Post>){
        val dbHelper = DBHelper(this)
        for (post in postsArray) {
            dbHelper.insertPost(post.id, post.userId, post.title, post.body)
        }
    }

    fun readFromApi(objectType: String): ArrayList<Any> {
        Log.d("DEBUG", "About to read url https://jsonplaceholder.typicode.com/"+ objectType + "s")
        val url = "https://jsonplaceholder.typicode.com/"+ objectType + "s"
        val json = URL(url).readText()
        return readJsonStream(json, objectType)
    }

    fun getUserScreenInfo(userArray: ArrayList<User>){
        val dbHelper = DBHelper(this)
        for (user in userArray){
            user.taskCtr = dbHelper.getCtr(user.id, "todo")
            user.postCtr = dbHelper.getCtr(user.id, "post")
        }
    }

    private fun onUserClick(user: User) {
        val intent = Intent(this, TaskListActivity::class.java)
        intent.putExtra("id", user.id)
        intent.putExtra("name", user.name)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DEBUG", "tekst na początek")

        var userArray: ArrayList<User>
        var todoArray: ArrayList<Todo>
        var postArray: ArrayList<Post>

        val thread = Thread(){
            run{
                // read data
               userArray = readFromApi("user") as ArrayList<User>
               todoArray = readFromApi("todo") as ArrayList<Todo>
               postArray = readFromApi("post") as ArrayList<Post>

                addUsersToDB(userArray)
                addTodosToDB(todoArray)
                addPostsToDB(postArray)

                getUserScreenInfo(userArray)
                Log.d("DEBUG", userArray[0].name + " " + userArray[0].taskCtr)


            }
            runOnUiThread(){
                //show
                Log.d("DEBUG", "show")
                setContentView(R.layout.activity_main)
                userAdapter = UserAdapter(mutableListOf())

                val userList = findViewById<RecyclerView>(R.id.rv_user)
                userList.adapter = userAdapter
                userList.layoutManager = LinearLayoutManager(this)

                for(user in userArray) {
                    userAdapter.addUser(user)
                }

                userAdapter.onItemClick = {user -> onUserClick(user)}
            }
        }
        thread.start()
    }
}