package com.panbadian.opiekun_lekow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonScan = findViewById<Button>(R.id.button)
        buttonScan.setOnClickListener{
            val intent = Intent(this, ActivityScanQR::class.java)
            startActivity(intent)
        }


    }
}