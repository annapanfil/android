package com.panbadian.opiekun_lekow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

private lateinit var prescriptionAdapter: PrescriptionAdapter

fun insertData(context: Context){
    val dbHelper = DBHelper(context)
    dbHelper.update()
    dbHelper.insertPatient(Patient("01234567890", "Leonardo", "Da Vinci", "15.07.1888"))
    dbHelper.insertPatient(Patient("12345678900", "Ezzio", "Auditore", "15.07.1888"))

    dbHelper.insertMed(Medicine(0, "Skyn Elite"))
    dbHelper.insertMed(Medicine(1, "Piwo"))
    dbHelper.insertMed(Medicine(2, "Medyczna marihuana"))

    dbHelper.insertPrescription(PrescriptionDB(0,"01234567890",0, "15.01.2022", "12.06.2023"))
    dbHelper.insertPrescription(PrescriptionDB(1,"01234567890",1, "15.01.2022", "12.06.2024"))
    dbHelper.insertPrescription(PrescriptionDB(2,"01234567890",2, "15.01.2022", "12.06.2025"))

    dbHelper.insertDose(0, Dose(0,"8:00", 4.0, "Od razu po przebudzeniu", true))
    dbHelper.insertDose(0, Dose(1, "15:00", 2.0, "Podaje tylko pielęgniarz ;)", false))
    dbHelper.insertDose(0, Dose(2, "12:00", 4.0, "-", true))
    dbHelper.insertDose(1, Dose(3, "13:00", 2.0, "Można więcej", false))
    dbHelper.insertDose(1, Dose(4, "14:00", 3.0, "Można więcej", false))
    dbHelper.insertDose(1, Dose(5, "15:00", 15.0, "Można więcej", false))
    dbHelper.insertDose(2, Dose(6, "8:00", 4.0, "-", true))
    dbHelper.insertDose(2, Dose(7, "10:00", 15.0, "Z plasterkiem cytryny", false))
}

class PatientCard : AppCompatActivity() {

    private fun onPrescriptionClick(prescription: Prescription) {
        Log.d("debug", "click " + prescription.medicine)
    }

    private fun onAddPrescriptionClick(pesel: String){
        // run Activity add prescription
        Log.d("debug", "click add")
        val intent = Intent(this, ActivityAddPrescription::class.java)
        intent.putExtra("pesel", pesel)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_card)

//        val userId = intent.getIntExtra("id", -3)
//        val userName = intent.getStringExtra("name")
        insertData(this)
        val dbHelper = DBHelper(this)

        // determine patient
        val patients = dbHelper.getPatients()
        val pesel = patients[0].pesel

        // get patient data from db
        val patient = dbHelper.getPatientByPesel(pesel)!!
        val prescriptions = dbHelper.getMeds(pesel)

        // bind data to fields
        val tvName = findViewById<TextView>(R.id.tv_name)
        tvName.text = patient.name + " " + patient.surname

        val rvMeds = findViewById<RecyclerView>(R.id.rv_meds)
        prescriptionAdapter = PrescriptionAdapter(mutableListOf())
        rvMeds.adapter = prescriptionAdapter
        rvMeds.layoutManager = LinearLayoutManager(this)
        prescriptionAdapter.onItemClick = { prescription -> onPrescriptionClick(prescription) }

        for (prescr in prescriptions) {
            prescriptionAdapter.addMed(prescr)
        }

        // handle the add button
        val fabAddPresr = findViewById<FloatingActionButton>(R.id.fab_add_prescription)
        fabAddPresr.setOnClickListener{onAddPrescriptionClick(patient.pesel)}
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK) {
//            finish()
//        }
//    }
}
