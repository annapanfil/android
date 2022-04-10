package com.example.intelligent_guess

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivityMy : AppCompatActivity() {
    private fun fillData(){
        val dbHelper = DBHelper(this)
        dbHelper.insert("Shrek", "123456")
        dbHelper.insert("Fiona", "123456789")
        dbHelper.insert("Osioł", "12345")
        dbHelper.insert("Kot_w_butach", "qwerty")
    }

    fun login(username: String, password: String): String? {
        try{
            val tvWarning = findViewById<TextView>(R.id.tv_warning)
            val dbHelper = DBHelper(this)

            val loginResult = dbHelper.search(username, password)
            when {
                loginResult >= 0 -> {
                    return username
                }
                loginResult == -1 -> { //wrong password
                    tvWarning.text = getString(R.string.wrong_password)
                    return null
                }
                loginResult == -2 -> { //wrong username
                    tvWarning.text = getString(R.string.wrong_login)
                    return null
                }
            }
            return null
        } catch (e: Throwable) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            return null
        }
    }

    private fun authorizeAccess(username: String){
        Toast.makeText(this,  getString(R.string.welcome) + " " + username, Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_my)

        val bLogin = findViewById<Button>(R.id.b_login)
        val bRegister = findViewById<Button>(R.id.b_register)
        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val tvWarning = findViewById<TextView>(R.id.tv_warning)
        val bRanking = findViewById<Button>(R.id.b_ranking)

        bLogin.setOnClickListener(){
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            if (username.isNotBlank()  && password.isNotBlank()){
                   if (login(username, password) != null){
                        authorizeAccess(username)
                   }
            }
            else
                tvWarning.text = getString(R.string.warning_fill_form)
        }

        bRegister.setOnClickListener(){
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            if (username.isNotBlank()  && password.isNotBlank()){
                try{
                    val dbHelper = DBHelper(this)
                    dbHelper.insert(username, password)
                    authorizeAccess(username)
                }
                catch (e: Throwable){
                    tvWarning.text = e.message
                }
            }
            else
                tvWarning.text = getString(R.string.warning_fill_form)
        }

        bRanking.setOnClickListener(){
            val intent = Intent(this, RankingActivity::class.java)
            startActivity(intent)
        }
    }
}