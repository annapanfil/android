package com.example.intelligent_guess

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,
    null, DATABASE_VER) {
    companion object {
        private const val DATABASE_VER = 1
        private const val DATABASE_NAME = "EDMTDB.db"
        //Table
        private const val TABLE_NAME = "Users"
        private const val COL_ID = "Id"
        private const val COL_LOGIN = "Login"
        private const val COL_PASSWORD = "Password"
        private const val COL_SCORE = "Score"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table_query = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_LOGIN TEXT UNIQUE, $COL_PASSWORD TEXT, $COL_SCORE INTEGER)")
        db!!.execSQL(create_table_query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    fun insert(login: String, password: String): Long {
        val db= this.writableDatabase
        val values = ContentValues()
        values.put(COL_LOGIN, login)
        values.put(COL_PASSWORD, password)
        values.put(COL_SCORE, 0)

        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }


    fun getScore(userId: Int): Int {
        val selectQuery = "SELECT $COL_SCORE FROM $TABLE_NAME WHERE $COL_ID = '$userId'"
        val db = this.writableDatabase
        val cursor =  db.rawQuery(selectQuery, null)
        val score = if(cursor.moveToFirst()){
            cursor.getInt(cursor.getColumnIndexOrThrow(COL_SCORE))
        } else -1
        cursor.close()
        db.close()
        return score
    }

    fun setScore(userId: Int, score: Int){
        val query = "UPDATE $TABLE_NAME SET $COL_SCORE = $score WHERE $COL_ID = '$userId'"
        val db = this.writableDatabase
        db.execSQL(query)
        db.close()
    }


    fun getUsers(howMany: Int): ArrayList<User> {
            val listUsers = ArrayList<User>()
            val selectQuery = "SELECT * FROM $TABLE_NAME ORDER BY $COL_SCORE DESC"
            val db = this.writableDatabase
            val cursor =  db.rawQuery(selectQuery, null)
            if(cursor.moveToFirst()){
                for (i in 0..howMany) {
                    val user = User()
                    user.id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
                    user.username = cursor.getString(cursor.getColumnIndexOrThrow(COL_LOGIN))
                    user.score = cursor.getInt(cursor.getColumnIndexOrThrow(COL_SCORE))
                    listUsers.add(user)
                    if (!cursor.moveToNext()){
                        break
                    }
                }
            }
            cursor.close()
            db.close()
            return listUsers
        }

    fun login(login: String, password: String): Int {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_LOGIN = '$login'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        val ret: Int
        if (cursor.moveToFirst()) {
            if (cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)) == password)
                ret = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
            else
                ret = -1
        } else
            ret = -2
        cursor.close()
        db.close()

        return ret
    }

}