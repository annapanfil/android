package com.example.intelligent_guess

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intelligent_guess.DBHelper
import com.example.intelligent_guess.R
import com.example.intelligent_guess.data.Result
import com.example.intelligent_guess.data.model.LoggedInUser
import java.io.IOException

class LoginActivityMy : AppCompatActivity() {
    fun login(username: String, password: String): String? {
        try{
            val tvWarning = findViewById<TextView>(R.id.tv_warning)
            val dbHelper = DBHelper(this)
            Toast.makeText(this, "Created DB", Toast.LENGTH_SHORT).show()

            val loginResult = dbHelper.search(username, password)
            Toast.makeText(this, "login result $loginResult", Toast.LENGTH_SHORT).show()

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

    fun logout() {
        // TODO: revoke authentication
    }

    fun authorizeAccess(username: String){
        Toast.makeText(this, getString(R.string.welcome) + username, Toast.LENGTH_LONG).show()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_my)

        val bLogin = findViewById<Button>(R.id.b_login)
        val bRegister = findViewById<Button>(R.id.b_register)
        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val tvWarning = findViewById<TextView>(R.id.tv_warning)

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
                if (login(username, password) != null){
                    try{
                        val dbHelper = DBHelper(this)
                        dbHelper.insert("login", "password")
                        authorizeAccess(username)
                    }
                    catch (e: Throwable){
                        val tvWarning = findViewById<TextView>(R.id.tv_warning)
                        tvWarning.text = e.message
                    }
                }
            }
            else
                tvWarning.text = getString(R.string.warning_fill_form)
        }
    }
}