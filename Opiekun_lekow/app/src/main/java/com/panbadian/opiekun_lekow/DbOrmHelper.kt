package com.panbadian.opiekun_lekow

import android.util.Log
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.select
import org.ktorm.logging.Slf4jLoggerAdapter
import org.ktorm.schema.*
import org.ktorm.support.sqlite.SQLiteDialect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate

object Patients : Table<Nothing>("Patients") {
    val pesel = varchar("pesel").primaryKey()
    val name = varchar("name")
    val surname = varchar("surname")
    val birthdate = date("birthdate")
}

object Medicines : Table<Nothing>("Medicines") {
    val id = int("id").primaryKey()
    val name = varchar("name")
}

object Prescriptions : Table<Nothing> ("Prescription") {
    val id = int("id").primaryKey()
    val patient = varchar("patient")
    val medicine = int("medicine")
    val date_from = date("date_from")
    val date_to = date("date_to")
}

object Doses : Table<Nothing> ("Doses") {
    val id = int ("id")
    val prescription = int("prescription")
    val hour = time("hour")
    val notes = varchar("notes")
    val quantity = float("quantity")
}

class DatabaseOrm()
{
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    private var database: Database = Database.connect("jdbc:mysql:sqlite:meds_guardian.db", logger = Slf4jLoggerAdapter(logger), dialect = SQLiteDialect())

    fun test(){
        for (row in database.from(Medicines).select()) {
            Log.d("medicine", row[Medicines.name].toString())
        }
    }

//    fun readMedsDoses(pesel: String)
//    {
//        val p = Prescriptions.aliased("p")
//        val m = Medicines.aliased("m")
////        val meds : MutableList<Medicine> = listOf()
//
//
//        val meds: List<Med> = database
//            .from(p)
//            .innerJoin(m, Medicines.id eq Prescriptions.medicine)
//            .select(Medicines.id, Medicines.name, Prescriptions.id)
//            .where { (Prescriptions.patient eq pesel)}
//            .map { row ->
//                Med(
//                    row[Medicines.id]!!,
//                    row[Medicines.name]!!,
//                    row[Prescriptions.id]!!//TODO: dodać konstruktor z pustą listą dawek
//                )
//        }
//
//        val d = Doses.aliased("d")
//        for (med in meds)
//        {
//            val doses = database
//                .from(d)
//                .select()
//                .where { (Doses.prescription eq )}
//                .map { row ->
//                    Dose(
//                        hour = row[Doses.hour]!!,
//                        quantity = row[Doses.quantity]!!,
//                        notes = row[Doses.notes]!!,
//                        given = row[Doses.given]
//                    )
//                }
//            med.doses = doses
//        }
//    }

    fun addMedicine(med: Med)
    {
        database.insert(Medicines){
            set(it.name, med.name)
        }
    }

    fun addPrescription(med_id: Int, patient: String, begin_date: LocalDate, end_date: LocalDate)
    {
        database.insert(Prescriptions){
            set(it.patient, patient)
            set(it.medicine, med_id)
            set(it.date_from, begin_date)
            set(it.date_to, end_date)
        }
    }

//    fun addPatient(patient: Patient)
//    {
//        database.insert(Patients){
//            set(it.pesel = patient.pesel)
//            set(it.name = patient.name)
//            set(it.surname = surname)
//            set(it.birthdate = birthdate)
//        }
//    }
}
