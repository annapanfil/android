package com.example.intelligent_guess

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todo_api.Comment
import com.example.todo_api.Post
import com.example.todo_api.Todo
import com.example.todo_api.User
import java.util.*
import kotlin.collections.ArrayList

class DBHelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,
    null, DATABASE_VER) {
    companion object {
        private const val DATABASE_VER = 1
        private const val DATABASE_NAME = "everything.db"
        //Tables
        private const val COL_ID = "Id"
        private const val COL_USER_ID = "User"

        private const val TABLE_USERS = "Users"
        private const val USER_NAME = "Name"
        private const val USER_EMAIL = "Email"

        private const val TABLE_TODOS = "Todos"
        private const val TODO_TITLE = "Title"
        private const val TODO_COMPLETED = "Completed"

        private const val TABLE_POSTS = "Posts"
        private const val POST_TITLE = "Title"
        private const val POST_BODY = "Body"

        private const val TABLE_COMMENTS = "Comments"
        private const val COMMENT_MAIL = "Email"
        private const val COMMENT_TITLE = "Title"
        private const val COMMENT_BODY = "Body"
        private const val COMMENT_POST_ID = "PostID"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        var create_table_query = ("CREATE TABLE $TABLE_USERS ($COL_ID INTEGER PRIMARY KEY, $USER_NAME TEXT, $USER_EMAIL TEXT)")
        db!!.execSQL(create_table_query)
        create_table_query = ("CREATE TABLE $TABLE_TODOS ($COL_ID INTEGER PRIMARY KEY, $COL_USER_ID INTEGER, $TODO_TITLE TEXT, $TODO_COMPLETED BOOLEAN)")
        db.execSQL(create_table_query)
        create_table_query = ("CREATE TABLE $TABLE_POSTS ($COL_ID INTEGER PRIMARY KEY, $COL_USER_ID INTEGER, $POST_TITLE TEXT, $POST_BODY TEXT)")
        db.execSQL(create_table_query)
        create_table_query = ("CREATE TABLE $TABLE_COMMENTS ($COL_ID INTEGER PRIMARY KEY, $COMMENT_POST_ID INTEGER, $COMMENT_MAIL TEXT, $COMMENT_TITLE TEXT, $COMMENT_BODY TEXT)")
        db.execSQL(create_table_query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_POSTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TODOS")
        onCreate(db)
    }

    fun insertUser(id: Int, name: String, email: String){
        val db= this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, id)
        values.put(USER_NAME, name)
        values.put(USER_EMAIL, email)

        db.insertWithOnConflict(TABLE_USERS, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        db.close()
    }

    fun insertTodo(id: Int, userId: Int, title: String, completed: Boolean){
        val db= this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, id)
        values.put(COL_USER_ID, userId)
        values.put(TODO_TITLE, title)
        values.put(TODO_COMPLETED, completed)
        db.insertWithOnConflict(TABLE_TODOS, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        db.close()
    }

    fun insertPost(id: Int, userId: Int, title: String, body: String){
        val db= this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, id)
        values.put(COL_USER_ID, userId)
        values.put(POST_TITLE, title)
        values.put(POST_BODY, body)

        db.insertWithOnConflict(TABLE_POSTS, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        db.close()
    }

    fun insertComment(id: Int, postId: Int, mail: String,  title: String, body: String){
        val db= this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, id)
        values.put(COMMENT_POST_ID, postId)
        values.put(COMMENT_MAIL, mail)
        values.put(COMMENT_TITLE, title)
        values.put(COMMENT_BODY, body)

        db.insertWithOnConflict(TABLE_COMMENTS, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        db.close()
    }

    fun getCtr(id: Int, objName: String): Int {
        val selectQuery = when (objName) {
            "todo" -> "SELECT count(*) AS ctr FROM $TABLE_TODOS WHERE $COL_USER_ID=$id"
            "post" -> "SELECT count(*) AS ctr FROM $TABLE_POSTS WHERE $COL_USER_ID=$id"
            else -> return -1
        }

        var ctr = -1
        val db = this.writableDatabase
        val cursor =  db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            ctr = cursor.getInt(cursor.getColumnIndexOrThrow("ctr"))
        }
        cursor.close()
        db.close()
        return ctr
    }

    fun getTodos(id: Int): ArrayList<Todo>{
        val selectQuery = "SELECT $TODO_TITLE, $TODO_COMPLETED FROM $TABLE_TODOS WHERE $COL_USER_ID=$id"

        val todoArray = ArrayList<Todo>()
        val db = this.writableDatabase
        val cursor =  db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            do{
                val todo = Todo()
                todo.title = cursor.getString(cursor.getColumnIndexOrThrow(TODO_TITLE))
                todo.completed = cursor.getInt(cursor.getColumnIndexOrThrow(TODO_COMPLETED)) == 1
                todoArray.add(todo)

            }while(cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return todoArray
    }

    fun getPosts(id: Int): ArrayList<Post>{
        val selectQuery = "SELECT $POST_TITLE, $COL_ID FROM $TABLE_POSTS WHERE $COL_USER_ID=$id"

        val postArray = ArrayList<Post>()
        val db = this.writableDatabase
        val cursor =  db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            do{
                val post = Post()
                post.title = cursor.getString(cursor.getColumnIndexOrThrow(POST_TITLE))
                post.id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
                postArray.add(post)
            }while(cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return postArray
    }


    fun getComments(id: Int): ArrayList<Comment>{
        val selectQuery = "SELECT $COMMENT_MAIL, $COMMENT_TITLE, $COMMENT_BODY FROM $TABLE_COMMENTS WHERE $COMMENT_POST_ID=$id"

        val commentArray = ArrayList<Comment>()
        val db = this.writableDatabase
        val cursor =  db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            do{
                val comment = Comment()
                comment.mail = cursor.getString(cursor.getColumnIndexOrThrow(COMMENT_MAIL))
                comment.title = cursor.getString(cursor.getColumnIndexOrThrow(COMMENT_TITLE))
                comment.body = cursor.getString(cursor.getColumnIndexOrThrow(COMMENT_BODY))
                commentArray.add(comment)
            }while(cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return commentArray
    }
}