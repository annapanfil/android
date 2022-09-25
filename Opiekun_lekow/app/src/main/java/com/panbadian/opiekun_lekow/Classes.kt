package com.panbadian.opiekun_lekow

import java.time.LocalDate
import java.util.*

class Dose(
    var hour: LocalDate,
    var quantity: Double,
    var notes: String,
    var given: Boolean
)

class Prescription(
    var id: Int = 0,
    var duration: String = "dd.mm.yyyy - dd.mm.yyyy",
    var dosages: LinkedList<Dose>
)

class Med(
    var id: Int,
    var name: String
)

//val doses: Array<Dose> = arrayOf(
//    Dose("7:00", 1.0, "Na czczo", false),
//    Dose("12:00", 0.5, "Z plasterkiem cytryny", false)
//)

//val meds: Array<Med> = arrayOf(
//    Med("Skyn Elite", "01.01.2022 - 31.12.2100", arrayOf(
//        OldDose("8:00", 4.0, "Od razu po przebudzeniu", true),
//        OldDose("15:00", 2.0, "Podaje tylko pielęgniarz ;)", false)
//    )),
//    Med("Piwo", "01.01.2022 - 15.05.2100", arrayOf(
//        OldDose("12:00", 4.0, "-", true),
//        OldDose("13:00", 2.0, "Można więcej", false),
//        OldDose("14:00", 3.0, "Można więcej", false),
//        OldDose("15:00", 15.0, "Można więcej", false)
//    )),
//    Med("Medyczna marihuana", "01.02.2022 - 01.06.2023", arrayOf(
//        OldDose("20:00", 4.0, "-", true),
//        OldDose("22:00", 15.0, "Odpalana od ogniska", false)
//    ))
//)