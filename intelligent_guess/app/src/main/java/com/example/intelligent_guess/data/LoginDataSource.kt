package com.example.intelligent_guess.data

import android.content.Context
import android.widget.Toast
import com.example.intelligent_guess.DBHelper
import com.example.intelligent_guess.R
import com.example.intelligent_guess.data.model.LoggedInUser
import com.example.intelligent_guess.ui.login.LoginActivity
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(_context: Context) {
    var context: Context = _context

    fun login(username: String, password: String): Result<LoggedInUser> {
        Toast.makeText(context, "Test login", Toast.LENGTH_LONG)

        try {
            var dbHelper = DBHelper(context)

            var loginResult = dbHelper.search(username, password)
            Toast.makeText(context, "login result = $loginResult", Toast.LENGTH_SHORT)
            if (loginResult >= 0) {
                val user = LoggedInUser(loginResult.toString(), username)
                return Result.Success(user)
            } else if (loginResult == -1) { //wrong password
                Toast.makeText(
                    context,
                    context.getString(R.string.wrong_password),
                    Toast.LENGTH_SHORT)
                    return Result.Error(IOException(context.getString(R.string.wrong_password)))
            } else if (loginResult == -2) { //wrong username
                // TODO: error and registration
            }
            return Result.Error(IOException("Gdzie leziesz?"))
            
        } catch (e: Throwable) {
            return Result.Error(IOException(context.getString(R.string.login_error), e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}