package com.example.bike_app.activities

fun String.normalize(): String {
    //source: http://tkadziolka.pl/blog/usuwanie_polskich_znakow.html
    val original = arrayOf("Ą", "ą", "Ć", "ć", "Ę", "ę", "Ł", "ł", "Ń", "ń", "Ó", "ó", "Ś", "ś", "Ź", "ź", "Ż", "ż")
    val normalized = arrayOf("A", "a", "C", "c", "E", "e", "L", "l", "N", "n", "O", "o", "S", "s", "Z", "z", "Z", "z")

    return this.map { char ->
        val index = original.indexOf(char.toString())
        if (index >= 0) normalized[index] else char
    }.joinToString("")
}