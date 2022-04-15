package com.example.todo_api
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.net.URL
import android.util.JsonReader
import com.example.intelligent_guess.DBHelper
import java.io.StringReader
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("DEBUG", "tekst na początek")

//        fun readArray(reader: JsonReader): ArrayList<User>{
//            var users = ArrayList<User>()
//
//            reader.beginArray();
//            while (reader.hasNext()) {
//                users.add(readUser(reader));
//            }
//            reader.endArray();
//            return users
//        }
//
//
//        fun readJsonStreamUser(json: String): ArrayList<User>{
//            val reader = JsonReader(StringReader(json))
//            try {
//                return readArray(reader)
//            } finally {
//                reader.close()
//            }
//        }

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
            val url = "https://jsonplaceholder.typicode.com/"+ objectType + "s"
            val json = URL(url).readText()
            return readJsonStream(json, objectType)
        }

        val thread = Thread() {

            run {
                val userArray = readFromApi("user") as ArrayList<User>
                val todoArray = readFromApi("todo") as ArrayList<Todo>
                val postArray = readFromApi("post") as ArrayList<Post>
                Log.d("DEBUG", userArray[0].name)
                Log.d("DEBUG", todoArray[0].title)
                Log.d("DEBUG", postArray[0].title)

                addUsersToDB(userArray)
                addTodosToDB(todoArray)
                addPostsToDB(postArray)
            }
            runOnUiThread() {
                //Co się wykona w głównym wątku po zakończeniu pracy danego wątku.
            }
        }

        thread.start()
    }
}