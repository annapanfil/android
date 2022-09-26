package com.panbadian.opiekun_lekow

class Dose(
    var id: Int,
    var hour: String,
    var quantity: Double,
    var notes: String,
    var given: Boolean
)

class Medicine (
    var id: Int,
    var name: String = "Med name"
){
    override fun toString(): String = name
}

class Patient(
    var pesel: String,
    var name: String,
    var surname: String,
    var birthdate: String
)

class Prescription(
    var id: Int,
    var medicine: String,
    var date_from: String,
    var date_to: String,
    var dosages: ArrayList<Dose>
)

class PrescriptionDB(
    var patient_pesel: String,
    var medicine: Int,
    var date_from: String,
    var date_to: String,
    var quantity: Int,
    var presr_code: Int
)
