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
    dbHelper.insertPatient(Patient("52041512345", "Leonardo", "Da Vinci", " 15.04.1452"))
    dbHelper.insertPatient(Patient("75030678900", "Michał", "Anioł", "06.03.1475"))

    dbHelper.insertMed(Medicine(0, "Ibuprofen"))
    dbHelper.insertMed(Medicine(1, "Hascovir"))
    dbHelper.insertMed(Medicine(2, "Amotaks"))

    dbHelper.insertPrescription(PrescriptionDB("52041512345",1, "15.01.2022", "25.01.2021", 15, 1245))
    dbHelper.insertPrescription(PrescriptionDB("52041512345",2, "25.12.2022", "25.06.2022", 5, 2323))
    dbHelper.insertPrescription(PrescriptionDB("52041512345",3, "13.01.2022", "13.03.2022", 11, 6666))

    dbHelper.insertDose(1, Dose(0,"8:00", 1.0, "W trakcie posiłku", true))
    dbHelper.insertDose(1, Dose(1, "12:00", 1.0, "Popijać wodą", false))
    dbHelper.insertDose(1, Dose(2, "15:00", 1.0, "-", true))
    dbHelper.insertDose(2, Dose(3, "7:00", 2.0, "Doustnie", false))
    dbHelper.insertDose(2, Dose(4, "15:00", 1.0, "j.w.", false))
    dbHelper.insertDose(2, Dose(5, "22:00", 1.5, "j.w.", false))
    dbHelper.insertDose(3, Dose(6, "6:00", 4.0, "Na czczo", true))
    dbHelper.insertDose(3, Dose(7, "18:00", 15.0, "1h po posiłku", false))
}

class ActivityPatientCard : AppCompatActivity() {
    private val dbHelper = DBHelper(this)
    private lateinit var pesel: String

    private fun refreshData(){
        // get prescriptions and add them to recycler view
        Log.d("debug", "refreshData")
        val prescriptions = dbHelper.getMeds(pesel)
        val rvMeds = findViewById<RecyclerView>(R.id.rv_meds)
        prescriptionAdapter = PrescriptionAdapter(mutableListOf())
        rvMeds.adapter = prescriptionAdapter
        rvMeds.layoutManager = LinearLayoutManager(this)
        prescriptionAdapter.onItemClick = { prescription -> onPrescriptionClick(prescription) }

        for (prescr in prescriptions) {
            prescriptionAdapter.addMed(prescr)
        }
    }

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

        pesel = intent.getStringExtra("pesel")!!
        insertData(this)

//        // determine patient
//        val patients = dbHelper.getPatients()
//        pesel = patients[0].pesel

        // get patient data from db
        val patient = dbHelper.getPatientByPesel(pesel)!!
        val prescriptions = dbHelper.getMeds(pesel)

        // bind data to fields
        val tvName = findViewById<TextView>(R.id.tv_name)
        tvName.text = patient.name + " " + patient.surname
        val tvBirthdate = findViewById<TextView>(R.id.tv_birthdate)
        tvBirthdate.text = patient.birthdate

        refreshData()

        // handle the add button
        val fabAddPresr = findViewById<FloatingActionButton>(R.id.fab_add_prescription)
        fabAddPresr.setOnClickListener{onAddPrescriptionClick(patient.pesel)}
    }

    override fun onRestart() {
        super.onRestart()
        refreshData()
    }
}
