package com.panbadian.opiekun_lekow

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bike_app.DBHelper

private lateinit var doseAdapter: DoseAdapter
private lateinit var prescriptionAdapter: PrescriptionAdapter

fun insertData(context: Context){
    val dbHelper = DBHelper(context)
    dbHelper.update()
    dbHelper.insertPatient(Patient("01234567890", "Leonardo", "Da Vinci", "15.07.1888"))
    dbHelper.insertPatient(Patient("12345678900", "Ezzio", "Auditore", "15.07.1888"))

    dbHelper.insertMed(Medicine(0, "Skyn Elite"))
    dbHelper.insertMed(Medicine(1, "Piwo"))
    dbHelper.insertMed(Medicine(2, "Medyczna marihuana"))

    dbHelper.insertPrescription(0, PrescriptionDB("01234567890",0, "15.01.2022", "12.06.2023"))
    dbHelper.insertPrescription(1, PrescriptionDB("01234567890",1, "15.01.2022", "12.06.2024"))
    dbHelper.insertPrescription(2, PrescriptionDB("01234567890",2, "15.01.2022", "12.06.2025"))

    dbHelper.insertDose(0, 0, Dose("8:00", 4.0, "Od razu po przebudzeniu", true))
    dbHelper.insertDose(0, 1, Dose("15:00", 2.0, "Podaje tylko pielęgniarz ;)", false))
    dbHelper.insertDose(0, 2, Dose("12:00", 4.0, "-", true))
    dbHelper.insertDose(1, 3, Dose("13:00", 2.0, "Można więcej", false))
    dbHelper.insertDose(1, 4, Dose("14:00", 3.0, "Można więcej", false))
    dbHelper.insertDose(1, 5, Dose("15:00", 15.0, "Można więcej", false))
    dbHelper.insertDose(2, 6, Dose("8:00", 4.0, "-", true))
    dbHelper.insertDose(2, 7, Dose("10:00", 15.0, "Z plasterkiem cytryny", false))
}

class PatientCard : AppCompatActivity() {

//    private fun onPostClick(post: Post) {
//        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
//        val intent = Intent(this, CommentListActivity::class.java)
//        intent.putExtra("postId", post.id)
//        intent.putExtra("name", post.title)
//        startActivityForResult(intent, 0)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_card)
        Log.d("Hello", "It is created")

//        val userId = intent.getIntExtra("id", -3)
//        val userName = intent.getStringExtra("name")

        //show
        insertData(this)
        val dbHelper = DBHelper(this)

        val patients = dbHelper.getPatients()
        val pesel = patients[0].pesel

        val patient = dbHelper.getPatientByPesel(pesel)!!
        val prescriptions = dbHelper.getMeds(pesel)

        val tvName = findViewById<TextView>(R.id.tv_name)
        tvName.text = patient.name + " " + patient.surname

        prescriptionAdapter = PrescriptionAdapter(mutableListOf())

        val medList = findViewById<RecyclerView>(R.id.rv_meds)
        medList.adapter = prescriptionAdapter
        medList.layoutManager = LinearLayoutManager(this)

        for (prescr in prescriptions){
            prescriptionAdapter.addMed(prescr)
        }



//        postAdapter.onItemClick = {post -> onPostClick(post)}

//        val bPrevious = findViewById<ImageButton>(R.id.b_previous)

//        bPrevious.setOnClickListener(){
//            finish()
//        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK) {
//            finish()
//        }
//    }
}
