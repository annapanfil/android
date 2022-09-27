package com.panbadian.opiekun_lekow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    fun init_authentication() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this@MainActivity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()
                    val intent = Intent(this@MainActivity, ActivityScanQR::class.java)
                    startActivity(intent)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric authentication")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Cancel")
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init_authentication()

        val buttonScan = findViewById<Button>(R.id.button)
        buttonScan.setOnClickListener{
            biometricPrompt.authenticate(promptInfo)
        }
    }
}