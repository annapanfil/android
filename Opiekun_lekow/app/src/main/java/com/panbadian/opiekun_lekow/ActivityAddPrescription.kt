package com.panbadian.opiekun_lekow

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class ActivityAddPrescription : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_prescription)

        val pesel = intent.getStringExtra("pesel")!!
        val dbHelper = DBHelper(this)
        val patient = dbHelper.getPatientByPesel(pesel)!!

        // display patient data in header
        val tvName = findViewById<TextView>(R.id.tv_patient)
        val tvPesel = findViewById<TextView>(R.id.tv_pesel)
        tvName.text = patient.name + " " + patient.surname
        tvPesel.text = patient.pesel

        // choose medicine
        // get medicines from db
        val meds = dbHelper.getAllMeds()
        meds.add(Medicine(-1, "<Nowy lek>"))

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.s_medicine)
        var medicine: Medicine? = null

        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, meds)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    medicine = meds[position]
                    if (medicine!!.name == "<Nowy lek>"){
                        val intent = Intent(this@ActivityAddPrescription, ActivityAddMed::class.java)
                        startActivity(intent)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    Toast.makeText(
                        applicationContext,
                        "Musisz wybrać lek",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // dates
        val myCalendar = Calendar.getInstance()
        val etDateBegin = findViewById<EditText>(R.id.et_date_begin)
        val etDateEnd = findViewById<EditText>(R.id.et_date_end)

        val dateBegin: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.YEAR, year)
                etDateBegin.setText("$day.$month.$year")
            }
        etDateBegin.setOnClickListener {
            DatePickerDialog(
                this@ActivityAddPrescription,
//                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                dateBegin,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val dateEnd: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.YEAR, year)
                etDateEnd.setText("$day.$month.$year")
            }
        etDateEnd.setOnClickListener {
            DatePickerDialog(
                this@ActivityAddPrescription,
//                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                dateEnd,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val etQuantity = findViewById<EditText>(R.id.et_quantity).text
        val etPrescription = findViewById<EditText>(R.id.et_prescr_nr).text
        val buttonOk = findViewById<FloatingActionButton>(R.id.fab_ok)
        val buttonCancel = findViewById<FloatingActionButton>(R.id.fab_cancel)

        buttonOk.setOnClickListener{
            val quantity = etQuantity.toString()
            val prescription = etPrescription.toString()
            if (quantity.isEmpty() or prescription.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Musisz wypełnić wszystkie pola",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                dbHelper.insertPrescription(
                    PrescriptionDB(
                        pesel,
                        medicine!!.id,
                        etDateBegin.text.toString(),
                        etDateEnd.text.toString(),
                        quantity.toInt(),
                        etPrescription.toString().toInt()
                    )
                )
                finish()
            }
        }

        buttonCancel.setOnClickListener {
            finish()
        }
    }
}