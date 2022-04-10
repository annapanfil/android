package com.example.intelligent_guess

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.intelligent_guess.DBHelper.Companion.COL_PASSWORD as COL_PASSWORD


class DBHelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,
    null, DATABASE_VER) {
    val context: Context = context
    companion object {
        private const val DATABASE_VER = 2
        private const val DATABASE_NAME = "EDMTDB.db"
        //Table
        private const val TABLE_NAME = "Users"
        private const val COL_ID = "Id"
        private const val COL_LOGIN = "Login"
        private const val COL_PASSWORD = "Password"
        private const val COL_SCORE = "Score"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_LOGIN TEXT UNIQUE, $COL_PASSWORD TEXT, $COL_SCORE INTEGER)")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    fun drop(){
        val db= this.writableDatabase
        val query ="DROP TABLE IF EXISTS $TABLE_NAME"
        db.rawQuery(query, null)
        db.close()
    }



    fun insert(login: String, password: String){
        val db= this.writableDatabase
        val values = ContentValues()
        values.put(COL_LOGIN, login)
        values.put(COL_PASSWORD, password)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    @SuppressLint("Range")
    fun select(): ArrayList<String> {
            val listUsers = ArrayList<String>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor =  db.rawQuery(selectQuery, null)
            if(cursor.moveToFirst()){
                do {
                    val user: String = cursor.getString(cursor.getColumnIndex(COL_LOGIN))
                    listUsers.add(user)
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return listUsers
        }

    fun search(login: String, password: String): Int{
        val query =  "SELECT * FROM $TABLE_NAME WHERE $COL_LOGIN = '$login'"
        val db = this.writableDatabase
        val cursor =  db.rawQuery(query, null)
        var ret: Int
        if(cursor.moveToFirst()){
            if(cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)) == password)
                ret = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
            else
                ret = -1
        }
        else
            ret = -2
        cursor.close()
        db.close()

        return ret
    }

    //CRUD
//    val allMessage:List<Message>
//        get(){
//            val listMessage = ArrayList<Message>()
//            val selectQuery = "SELECT * FROM $TABLE_NAME"
//            val db = this.writableDatabase
//            val cursor =  db.rawQuery(selectQuery, null)
//            if(cursor.moveToFirst()){
//                do {
//                    val message = Message()
//                    message.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
//                    message.info = cursor.getString(cursor.getColumnIndex(COL_MESSAGE))
//                    message.type = cursor.getString(cursor.getColumnIndex(COL_TYPE))
//                    message.key = cursor.getString(cursor.getColumnIndex(COL_PASS))
//
//                    listMessage.add(message)
//                } while (cursor.moveToNext())
//            }
//            db.close()
//            return listMessage
//        }
////
//    fun addMessage(message:Message){
//        val db= this.writableDatabase
//        val values = ContentValues()
//        values.put(COL_MESSAGE, message.info)
//        values.put(COL_TYPE, message.type)
//        values.put(COL_KEY, message.key)
//
//        db.insert(TABLE_NAME, null, values)
//        db.close()
//    }
//
//    fun testOne(key:String): Boolean {
//        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COL_KEY = '$key'"
//        val db = this.writableDatabase
//        val cursor = db.rawQuery(selectQuery, null)
//        if (cursor.moveToFirst()){
//            if(key == cursor.getString(cursor.getColumnIndex(COL_KEY))) {
//                return false
//            }
//        }
//        return true
//    }

}