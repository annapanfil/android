package com.example.bike_app

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
        private const val DATABASE_NAME = "bike_routes.db"
        private const val TABLE = "ROUTES"
        private const val COL_ID = "ID"
        private const val COL_NAME = "Name"
        private const val COL_DESCR = "Description"
        private const val COL_TRACK = "Track"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var createTableQuery = ("CREATE TABLE $TABLE " +
                "($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_NAME TEXT, " +
                "$COL_DESCR TEXT," +
                "$COL_TRACK TEXT)")
        db!!.execSQL(createTableQuery)
    }

    fun update() {
        val db= this.writableDatabase
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }

    fun insertTrack(name: String, description: String, track: String){
        val db= this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, name)
        values.put(COL_DESCR, description)
        values.put(COL_TRACK, track)

        db.insertWithOnConflict(TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        db.close()
    }


    fun getDetails(id: Int): String{
        val selectQuery = "SELECT $COL_DESCR FROM $TABLE WHERE $COL_ID=$id"
        val db = this.writableDatabase
        var details: String = "Wrong id. No details here"

        val cursor =  db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            details =  cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCR)) + "\n" + cursor.getString(cursor.getColumnIndexOrThrow(COL_TRACK))
        }
        cursor.close()
        db.close()
        return details
    }

    fun getNames(): ArrayList<String>{
        val selectQuery = "SELECT $COL_NAME FROM $TABLE"

        val names = ArrayList<String>()
        val db = this.writableDatabase
        val cursor =  db.rawQuery(selectQuery, null)

        if(cursor.moveToFirst()){
            do{
                names.add(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)))
            }while(cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return names
    }
}