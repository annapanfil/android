package com.example.bike_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class DBHelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,
    null, DATABASE_VER) {
    companion object {
        private const val DATABASE_VER = 1
        private const val DATABASE_NAME = "bike_routes.db"

        private const val TABLE_ROUTES = "ROUTES"
        private const val TABLE_TIMES = "TIMES"

        private const val COL_ID = "ID"
        private const val COL_NAME = "Name"
        private const val COL_DESCR = "Description"
        private const val COL_TRACK = "Track"

        private const val COL_TRACK_ID = "TrackId"
        private const val COL_TIME = "Time"
        private const val COL_DATE = "Date"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        var createTableQuery = ("CREATE TABLE $TABLE_ROUTES " +
                "($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_NAME TEXT, " +
                "$COL_DESCR TEXT," +
                "$COL_TRACK TEXT)")
        db!!.execSQL(createTableQuery)

        createTableQuery = ("CREATE TABLE $TABLE_TIMES " +
                "($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_TRACK_ID INTEGER, " +
                "$COL_TIME INTEGER, " +
                "$COL_DATE TEXT)")
        db!!.execSQL(createTableQuery)
    }

    fun update() {
        val db= this.writableDatabase
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_ROUTES")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_TIMES")
        onCreate(db)
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_ROUTES")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_TIMES")
        onCreate(db)
    }

    // INSERT

    fun insertTrack(name: String, description: String, track: String){
        val db= this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, name)
        values.put(COL_DESCR, description)
        values.put(COL_TRACK, track)

        db.insertWithOnConflict(TABLE_ROUTES, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        db.close()
    }

    fun insertTime(trackId: Long, timeInSecs: Int){
        val date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)

        val db= this.writableDatabase
        val values = ContentValues()

        values.put(COL_TRACK_ID, trackId)
        values.put(COL_TIME, timeInSecs)
        values.put(COL_DATE, date)

        db.insertWithOnConflict(TABLE_TIMES, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        db.close()
    }

    // ------------------ GETTERS ------------------

    fun getDetails(id: Long): String{
        val selectQuery = "SELECT $COL_DESCR, $COL_TRACK FROM $TABLE_ROUTES WHERE $COL_ID=$id"
        val db = this.writableDatabase
        var details: String = "Wrong id. No details here"

        val cursor =  db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            details =  cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCR)) + "\n\n" + cursor.getString(cursor.getColumnIndexOrThrow(COL_TRACK))
        }
        cursor.close()
        db.close()
        return details
    }

    fun getNames(): ArrayList<String>{
        val selectQuery = "SELECT $COL_NAME FROM $TABLE_ROUTES"

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

    fun getName(id: Long): String?{
        val selectQuery = "SELECT $COL_NAME FROM $TABLE_ROUTES WHERE $COL_ID = $id"

        var name: String? = null
        val db = this.writableDatabase
        val cursor =  db.rawQuery(selectQuery, null)

        if(cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME))
        }
        cursor.close()
        db.close()
        return name
    }

    fun getBestTime(trackId: Long): Record {
        val selectQuery = "SELECT $COL_TIME, $COL_DATE FROM $TABLE_TIMES WHERE $COL_TRACK_ID = $trackId ORDER BY $COL_TIME ASC"

        val time: Int
        val date: String

        val db = this.writableDatabase
        val cursor =  db.rawQuery(selectQuery, null)

        if(cursor.moveToFirst()){
            time = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TIME))
            date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE))
        }
        else{
            time = -1
            date = "brak rekordu"
        }

        cursor.close()
        db.close()

        val record = Record(time, date)
        return record
    }

    fun getTimes(trackId: Long): ArrayList<Record>{
        val selectQuery = "SELECT $COL_TIME, $COL_DATE FROM $TABLE_TIMES WHERE $COL_TRACK_ID = $trackId ORDER BY $COL_TIME ASC"

        val records = ArrayList<Record>()

        val db = this.writableDatabase
        val cursor =  db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()){
            do{
                val time: Int = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TIME))
                val date: String = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE))
                records.add(Record(time, date))
            }while(cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return records
    }
}