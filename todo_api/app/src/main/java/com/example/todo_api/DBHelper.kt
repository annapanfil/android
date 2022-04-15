package com.example.intelligent_guess

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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

    }

    override fun onCreate(db: SQLiteDatabase?) {
        var create_table_query = ("CREATE TABLE $TABLE_USERS ($COL_ID INTEGER PRIMARY KEY, $USER_NAME TEXT, $USER_EMAIL TEXT)")
        db!!.execSQL(create_table_query)
        create_table_query = ("CREATE TABLE $TABLE_TODOS ($COL_ID INTEGER PRIMARY KEY, $COL_USER_ID INTEGER, $TODO_TITLE TEXT, $TODO_COMPLETED BOOLEAN)")
        db!!.execSQL(create_table_query)
        create_table_query = ("CREATE TABLE $TABLE_POSTS ($COL_ID INTEGER PRIMARY KEY, $COL_USER_ID INTEGER, $POST_TITLE TEXT, $POST_BODY TEXT)")
        db!!.execSQL(create_table_query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_POSTS")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_TODOS")
        onCreate(db!!)
    }

    fun insertUser(id: Int, name: String, email: String){
        val db= this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, id)
        values.put(USER_NAME, name)
        values.put(USER_EMAIL, email)

        db.insert(TABLE_USERS, null, values)
        db.close()
    }

    fun insertTodo(id: Int, userId: Int, title: String, completed: Boolean){
        val db= this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, id)
        values.put(COL_USER_ID, userId)
        values.put(TODO_TITLE, title)
        values.put(TODO_COMPLETED, completed)

        db.insert(TABLE_TODOS, null, values)
        db.close()
    }

    fun insertPost(id: Int, userId: Int, title: String, body: String){
        val db= this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, id)
        values.put(COL_USER_ID, userId)
        values.put(POST_TITLE, title)
        values.put(POST_BODY, body)

        db.insert(TABLE_POSTS, null, values)
        db.close()
    }

//
//    fun getScore(userId: Int): Int {
//        val selectQuery = "SELECT $COL_SCORE FROM $TABLE_NAME WHERE $COL_ID = '$userId'"
//        val db = this.writableDatabase
//        val cursor =  db.rawQuery(selectQuery, null)
//        val score = if(cursor.moveToFirst()){
//            cursor.getInt(cursor.getColumnIndexOrThrow(COL_SCORE))
//        } else -1
//        cursor.close()
//        db.close()
//        return score
//    }
//
//    fun setScore(userId: Int, score: Int){
//        val query = "UPDATE $TABLE_NAME SET $COL_SCORE = $score WHERE $COL_ID = '$userId'"
//        val db = this.writableDatabase
//        db.execSQL(query)
//        db.close()
//    }
//
//
//    fun getUsers(howMany: Int): ArrayList<User> {
//        val listUsers = ArrayList<User>()
//        val selectQuery = "SELECT * FROM $TABLE_NAME ORDER BY $COL_SCORE DESC"
//        val db = this.writableDatabase
//        val cursor =  db.rawQuery(selectQuery, null)
//        if(cursor.moveToFirst()){
//            for (i in 0..howMany) {
//                val user = User()
//                user.id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
//                user.username = cursor.getString(cursor.getColumnIndexOrThrow(COL_LOGIN))
//                user.score = cursor.getInt(cursor.getColumnIndexOrThrow(COL_SCORE))
//                listUsers.add(user)
//                if (!cursor.moveToNext()){
//                    break
//                }
//            }
//        }
//        cursor.close()
//        db.close()
//        return listUsers
//    }
//
//    fun login(login: String, password: String): Int {
//        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_LOGIN = '$login'"
//        val db = this.writableDatabase
//        val cursor = db.rawQuery(query, null)
//        val ret: Int
//        if (cursor.moveToFirst()) {
//            if (cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)) == password)
//                ret = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
//            else
//                ret = -1
//        } else
//            ret = -2
//        cursor.close()
//        db.close()
//
//        return ret
//    }

}