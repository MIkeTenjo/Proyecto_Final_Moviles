package com.example.proyecto_final.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_final.R

class Main_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEmpezar = findViewById<Button>(R.id.btnEmpezar)
        val btnSalir = findViewById<Button>(R.id.btnSalir)

        btnEmpezar.setOnClickListener {
            val intent = Intent(this, PartidasActivity::class.java)
            startActivity(intent)
        }

        btnSalir.setOnClickListener {
            finishAffinity()
        }
    }
}
