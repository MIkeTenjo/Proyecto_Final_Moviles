package com.example.tarea_3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.core.content.edit

class Personaje1Activity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    //Variable de preferencias compartidas para guardar los datos.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaje1)

        val nombre1 = findViewById<EditText>(R.id.nombre1)
        val btnBorrar = findViewById<Button>(R.id.btnBorrar1)
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente1)

        // Inicializa las preferencias
        prefs = getSharedPreferences("DatosPersonajes", MODE_PRIVATE)

        //  Cargar el valor guardado si existe
        val nombreGuardado = prefs.getString("nombre1", "")
        nombre1.setText(nombreGuardado)

        //  Botón borrar
        btnBorrar.setOnClickListener {
            nombre1.text.clear()
            // Borra también del almacenamiento
            prefs.edit { remove("nombre1") }
        }

        //  Botón siguiente
        btnSiguiente.setOnClickListener {
            val nombre = nombre1.text.toString()
            // Guarda el nombre en SharedPreferences
            prefs.edit { putString("nombre1", nombre) }

            val intent = Intent(this, Personaje2Activity::class.java)
            startActivity(intent)
        }
    }

    //  Guarda automáticamente cuando se pause la Activity
    override fun onPause() {
        super.onPause()
        val nombre1 = findViewById<EditText>(R.id.nombre1)
        prefs.edit { putString("nombre1", nombre1.text.toString()) }
    }
}
