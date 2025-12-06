package com.example.tarea_3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
class MainActivity : AppCompatActivity() {

    private lateinit var btnIniciar: Button
    private lateinit var btnSalir: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnIniciar = findViewById(R.id.btnIniciar)
        btnSalir = findViewById(R.id.btnSalir)

        // Cuando se presiona "Iniciar", abre la primera pantalla del personaje
        btnIniciar.setOnClickListener {
            val intent = Intent(this, Personaje1Activity::class.java)
            startActivity(intent)
        }

        // Cuando se presiona "Salir", cierra la aplicación
        btnSalir.setOnClickListener {
            finishAffinity() // Cierra todas las actividades de la app
        }
    }
}