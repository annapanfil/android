package com.panbadian.opiekun_lekow

class Dose(
    var hour: String = "12:00",
    var quantity: Double = 1.0,
    var notes: String = "Note",
    var given: Boolean = false
)

class Med (
    var name: String = "Med name",
    var duration: String = "dd.mm.yyyy - dd.mm.yyyy",
    var dosages: Array<Dose>
)

val doses: Array<Dose> = arrayOf(
    Dose("7:00", 1.0, "Na czczo", false),
    Dose("12:00", 0.5, "Z plasterkiem cytryny", false)
)

val meds: Array<Med> = arrayOf(
    Med("Skyn Elite", "01.01.2022 - 31.12.2100", arrayOf(
        Dose("8:00", 4.0, "Od razu po przebudzeniu", true),
        Dose("15:00", 2.0, "Podaje tylko pielęgniarz ;)", false)
    )),
    Med("Piwo", "01.01.2022 - 15.05.2100", arrayOf(
        Dose("12:00", 4.0, "-", true),
        Dose("13:00", 2.0, "Można więcej", false),
        Dose("14:00", 3.0, "Można więcej", false),
        Dose("15:00", 15.0, "Można więcej", false)
    )),
    Med("Medyczna marihuana", "01.02.2022 - 01.06.2023", arrayOf(
        Dose("8:00", 4.0, "-", true),
        Dose("10:00", 15.0, "Można więcej", false)
    ))
)