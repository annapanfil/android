package com.panbadian.opiekun_lekow

class Dose(
    var hour: String = "12:00",
    var quantity: Double = 1.0,
    var notes: String = "Note",
    var given: Boolean = false
)

class Drug (
    var name: String = "Drug name",
    var dosages: ArrayList<Dose>
)

val doses: Array<Dose> = arrayOf(
    Dose("7:00", 1.0, "Na czczo", false),
    Dose("12:00", 0.5, "Z plasterkiem cytryny", false),
    Dose("16:00", 2.0, "podaje tylko pielęgniarz ;)", true)
)