package com.panbadian.opiekun_lekow

import android.content.ContentValues
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
        private const val COL_GIVEN = "given"

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
                "$COL_PRESCRIPTION INTEGER," +
                "$COL_HOUR TEXT, " +
                "$COL_NOTES TEXT," +
                "$COL_QUANT FLOAT," +
                "$COL_GIVEN BOOLEAN)"
                )

        db.execSQL(createTableQuery)

        createTableQuery = ("CREATE TABLE $TABLE_PRESCRIPTIONS " +
                "($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_MED INTEGER," +
                "$COL_PATIENT TEXT, " +
                "$COL_FROM TEXT," +
                "$COL_TO TEXT)")
        db.execSQL(createTableQuery)
        Log.d("debug", "created")
    }


    fun update() {
        val db = this.writableDatabase
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_DOSES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MEDICINES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PATIENTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRESCRIPTIONS")
        onCreate(db)
        db.close()
        Log.d("debug", "deleted")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_PATIENTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MEDICINES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DOSES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRESCRIPTIONS")
        onCreate(db)
        Log.d("debug", "deleted")
    }

    fun insertPatient(patient: Patient) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_PESEL, patient.pesel)
        values.put(COL_NAME, patient.name)
        values.put(COL_SURNAME, patient.surname)
        values.put(COL_BIRTHDATE, patient.birthdate)

        db.insertWithOnConflict(TABLE_PATIENTS, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        db.close()
    }

    fun insertMed(med: Medicine) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, med.id)
        values.put(COL_NAME, med.name)

        db.insertWithOnConflict(TABLE_MEDICINES, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        db.close()
    }

    fun insertDose(prescr: Int, dose: Dose) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_PRESCRIPTION, prescr)
        values.put(COL_ID, dose.id)
        values.put(COL_HOUR, dose.hour)
        values.put(COL_NOTES, dose.notes)
        values.put(COL_QUANT, dose.quantity)
        values.put(COL_GIVEN, dose.given)

        db.insertWithOnConflict(TABLE_DOSES, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        db.close()
    }

    fun insertPrescription(prescr: PrescriptionDB) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, prescr.id)
        values.put(COL_PATIENT, prescr.patient_pesel)
        values.put(COL_MED, prescr.medicine)
        values.put(COL_FROM, prescr.date_from)
        values.put(COL_TO, prescr.date_to)

        db.insertWithOnConflict(TABLE_PRESCRIPTIONS, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        db.close()
    }

    fun getPatients(): ArrayList<Patient> {
        val selectQuery = "SELECT * FROM $TABLE_PATIENTS"

        val patients = ArrayList<Patient>()
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                patients.add(
                    Patient(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_PESEL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_SURNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BIRTHDATE))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return patients
    }

    fun getPatientByPesel(pesel: String): Patient? {
        val selectQuery = "SELECT * FROM $TABLE_PATIENTS WHERE $COL_PESEL = \"$pesel\""

        var patient: Patient? = null
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            patient = Patient(
                cursor.getString(cursor.getColumnIndexOrThrow(COL_PESEL)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_SURNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_BIRTHDATE))
            )
            Log.d("debug", "Patient found")
        }
        cursor.close()
        db.close()
        Log.d("debug", "getPatient")
        return patient
    }

    fun getMeds(pesel: String): ArrayList<Prescription> {
        var selectQuery =
            "SELECT $TABLE_MEDICINES.$COL_ID, $COL_NAME FROM $TABLE_MEDICINES JOIN $TABLE_PRESCRIPTIONS ON $TABLE_MEDICINES.$COL_ID = $TABLE_PRESCRIPTIONS.$COL_MED WHERE $COL_PATIENT = \"$pesel\""

        val prescriptions = ArrayList<Prescription>()
        val meds = ArrayList<Medicine>()

        val db = this.writableDatabase
        var cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do{
                meds.add(
                    Medicine(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME))
                    )
                )
            }while(cursor.moveToNext())
        }
        cursor.close()

        for (med in meds) {
            val doses = ArrayList<Dose>()
            selectQuery = "SELECT * FROM $TABLE_DOSES JOIN $TABLE_PRESCRIPTIONS ON ${TABLE_DOSES}.${COL_PRESCRIPTION} = ${TABLE_PRESCRIPTIONS}.${COL_ID} WHERE $COL_PATIENT = \"$pesel\" AND $COL_MED = ${med.id}"
            Log.d("debug", selectQuery)
            cursor = db.rawQuery(selectQuery, null)
            var dateTo: String = ""
            var dateFrom: String = ""
            var id: Int = -1

            if (cursor.moveToFirst()) {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
                dateFrom = cursor.getString(cursor.getColumnIndexOrThrow(COL_FROM))
                dateTo = cursor.getString(cursor.getColumnIndexOrThrow(COL_TO))
                Log.d("debug", "jest 1")
                do{
                    doses.add(
                        Dose(
                            cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_HOUR)),
                            cursor.getDouble(cursor.getColumnIndexOrThrow(COL_QUANT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTES)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COL_GIVEN)) > 0,
                        )
                    )
                }while(cursor.moveToNext())
            }
            prescriptions.add(Prescription(
                id, med.name, dateFrom, dateTo, doses)
            )
            cursor.close()
        }

        db.close()
        return prescriptions
    }

    fun changeGiven(doseId: Int, status: Boolean)
    {
        val db = this.writableDatabase.apply {
            execSQL("UPDATE $TABLE_DOSES SET $COL_GIVEN = $status WHERE $COL_ID = $doseId")
            close()
        }
    }

    private fun deleteById(table: String, id: Int){
        val db = this.writableDatabase.apply {
            execSQL("DELETE FROM $table WHERE $COL_ID = $id")
            close()
        }
    }

    fun deletePrescription(id: Int){
        deleteById(TABLE_PRESCRIPTIONS, id)
    }

    fun deleteDose(id: Int){
        deleteById(TABLE_DOSES, id)
    }
}