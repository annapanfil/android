package com.example.intelligent_guess

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    fun givePoints(guessCounter: Int): Int {
        when {
            guessCounter == 1 -> return 5
            guessCounter <= 4 -> return 3
            guessCounter <= 6 -> return 2
            guessCounter <= 10 -> return 1
        }
        return 0
    }

    private fun showAlert(title: String, message: String){
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton("Jeszcze raz"){ _: DialogInterface, _: Int ->}

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun setRecord(dbHelper: DBHelper, id: Int, score: Int){
        dbHelper.setScore(id, score)
    }

    private fun getRecord(dbHelper: DBHelper, id: Int): Int {
        return dbHelper.getScore(id)
    }

    private fun checkText(strGuess: String): Boolean {
        if (strGuess.contains(".")){
            Toast.makeText(applicationContext, "$strGuess nie jest liczbą całkowitą", Toast.LENGTH_SHORT).show()
            return false
        }
        if (strGuess.length > 3){
            Toast.makeText(applicationContext, "Podana liczba $strGuess nie mieści się w zakresie", Toast.LENGTH_SHORT).show()
            return false
        }
        if (strGuess.isEmpty()) {
            Toast.makeText(applicationContext, "Najpierw podaj liczbę", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val id = intent.getIntExtra("id", -1)
//        supportActionBar?.hide()

        val MAX_TRIES = 10
        val buttonOk = findViewById<Button>(R.id.buttonOk)
        val buttonRank = findViewById<Button>(R.id.buttonRank)
        val buttonNewGame = findViewById<Button>(R.id.buttonNewGame)

        val editTextNumber= findViewById<EditText>(R.id.editTextNumber).text

        val textScore = findViewById<TextView>(R.id.textViewScore)
        val textTries = findViewById<TextView>(R.id.textViewTries)
        val dbHelper = DBHelper(this)

        var secret =  Random.nextInt(0,20)
        var guessCounter = 0
        var score = getRecord(dbHelper, id)
        textScore.text = "Twój wynik: $score"
        textTries.text = "Masz jeszcze $MAX_TRIES prób"


        buttonOk.setOnClickListener{
            val strGuess = editTextNumber.toString()
            if (checkText(strGuess)){
                val guess = strGuess.toInt()
                if (guess < 0 || guess > 20)
                    Toast.makeText(applicationContext, "Podana liczba $guess nie mieści się w zakresie", Toast.LENGTH_SHORT).show()
                else {
                    guessCounter++
                    textTries.text = "Masz jeszcze " + (MAX_TRIES - guessCounter) + " prób"

                    if (guess == secret){
                        showAlert("Wygrałeś", "$guess to poprawna liczba\nZgadłeś ją w $guessCounter ruchach")

                        score += givePoints(guessCounter)
                        setRecord(dbHelper, id, score)
                        textScore.text = "Twój wynik: $score"

                        guessCounter = 0
                        textTries.text = "Masz jeszcze $MAX_TRIES prób"
                        secret =  Random.nextInt(0,20)

                    }else {
                        if (guess < secret)
                            Toast.makeText(applicationContext, "Twoja liczba jest za mała", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(applicationContext, "Twoja liczba jest za duża", Toast.LENGTH_SHORT).show()

                        if (guessCounter >= MAX_TRIES) {
                            showAlert("Przegrałeś","Przekroczyłeś maksymalną liczbę ruchów. Ukryta liczba to: $secret")
                            guessCounter = 0
                            textTries.text = "Masz jeszcze $MAX_TRIES prób"
                            secret =  Random.nextInt(0,20)
                        }
                    }
                }
            }
            editTextNumber.clear()
        }

        buttonNewGame.setOnClickListener(){
            guessCounter = 0
            textTries.text = "Masz jeszcze $MAX_TRIES prób"
            secret =  Random.nextInt(0,20)
        }

        buttonRank.setOnClickListener{
            val intent = Intent(this, RankingActivity::class.java)
            startActivity(intent)
        }
    }
}