package com.example.bike_app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,
    null, DATABASE_VER) {
    companion object {
        private const val DATABASE_VER = 1
        private const val DATABASE_NAME = "meds_guardian.db"

        private const val COL_ID = "id"

        private const val TABLE_PATIENTS = "PATIENTS"
        private const val COL_PESEL = "pesel"
        private const val COL_NAME = "name"
        private const val COL_SURNAME = "surname"
        private const val COL_BIRTHDATE = "birthdate"

        private const val TABLE_MEDICINES = "MEDICINES"

        private const val TABLE_DOSES = "DOSES"
        private const val COL_PRESCRIPTION = "prescription"
        private const val COL_HOUR = "hour"
        private const val COL_NOTES = "hotes"
        private const val COL_QUANT = "quantity"

        private const val TABLE_PRESCRIPTIONS = "PRESCRIPTIONS"
        private const val COL_PATIENT = "patient"
        private const val COL_MED = "medicine"
        private const val COL_FROM = "date_from"
        private const val COL_TO = "date_to"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("baza", "tworzę bazę")
        var createTableQuery = ("CREATE TABLE $TABLE_PATIENTS " +
                "($COL_PESEL TEXT PRIMARY KEY, " +
                "$COL_NAME TEXT, " +
                "$COL_SURNAME TEXT," +
                "$COL_BIRTHDATE TEXT)")
        db!!.execSQL(createTableQuery)

        createTableQuery = ("CREATE TABLE $TABLE_MEDICINES " +
                "($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_NAME TEXT)")
        db.execSQL(createTableQuery)

        createTableQuery = ("CREATE TABLE $TABLE_DOSES " +
                "($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_PRESCRIPTION INTEGER" +
                "$COL_HOUR DATETIME, " +
                "$COL_NOTES TEXT," +
                "$COL_QUANT FLOAT)")
        db.execSQL(createTableQuery)

        createTableQuery = ("CREATE TABLE $TABLE_PRESCRIPTIONS " +
                "($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_MED INTEGER" +
                "$COL_PATIENT INTEGER, " +
                "$COL_FROM DATE," +
                "$COL_TO DATE)")
        db.execSQL(createTableQuery)
    }


//    fun update() {
//        val db= this.writableDatabase
//        db!!.execSQL("DROP TABLE IF EXISTS $TABLE")
//        onCreate(db)
//        db.close()
//    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_PATIENTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MEDICINES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DOSES")
        onCreate(db)
    }
//
//    fun insertPatient(pesel: String, name: String, surname: String, birthdate: String){
//        val db= this.writableDatabase
//        val values = ContentValues()
//        values.put(COL_PESEL, pesel)
//        values.put(COL_NAME, name)
//        values.put(COL_SURNAME, surname)
//        values.put(COL_BIRTHDATE, birthdate)
//
//        db.insertWithOnConflict(TABLE_PATIENTS, null, values, SQLiteDatabase.CONFLICT_IGNORE)
//        db.close()
//    }
//
//    fun insertMed(id: Int, name: String, dateFrom: String, dateTo: String){
//        val db= this.writableDatabase
//        val values = ContentValues()
//        values.put(COL_ID, id)
//        values.put(COL_NAME, name)
//        values.put(COL_FROM, dateFrom)
//        values.put(COL_TO, dateTo)
//
//        db.insertWithOnConflict(TABLE_MEDICINES, null, values, SQLiteDatabase.CONFLICT_IGNORE)
//        db.close()
//    }
//
//    fun insertDose(id: Int, hour: String, notes: String, quantity: Float){
//        val db= this.writableDatabase
//        val values = ContentValues()
//        values.put(COL_ID, id)
//        values.put(COL_HOUR, hour)
//        values.put(COL_NOTES, notes)
//        values.put(COL_QUANT, quantity)
//
//        db.insertWithOnConflict(TABLE_DOSES, null, values, SQLiteDatabase.CONFLICT_IGNORE)
//        db.close()
//    }
//
//    fun getNames(): ArrayList<String>{
//        val selectQuery = "SELECT $COL_NAME FROM $TABLE_PATIENTS"
//
//        val names = ArrayList<String>()
//        val db = this.writableDatabase
//        val cursor =  db.rawQuery(selectQuery, null)
//
//        if(cursor.moveToFirst()){
//            do{
//                names.add(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)))
//            }while(cursor.moveToNext())
//        }
//        cursor.close()
//        db.close()
//        return names
//    }
//
//    fun getMeds(pesel: String): ArrayList<Med>{
//        val selectQuery = "SELECT * FROM $TABLE_DOSES JOIN $TABLE_MEDICINES WHERE $COL_PATIENT = $pesel"
//
//        var meds: ArrayList<Med>
//        var doses: ArrayList<OldDose>
//
//        var name: String? = null
//        val db = this.writableDatabase
//        val cursor =  db.rawQuery(selectQuery, null)
//
//        if(cursor.moveToFirst()){
//            name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME))
//        }
//        cursor.close()
//        db.close()
//        return name
//    }
}