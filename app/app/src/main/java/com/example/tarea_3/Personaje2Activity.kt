package com.example.tarea_3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.core.content.edit

class Personaje2Activity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    //Variable de preferencias compartidas para guardar los datos.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaje2)

        val nombre2 = findViewById<EditText>(R.id.nombre2) // Campo de texto del personaje
        val btnBorrar = findViewById<Button>(R.id.btnBorrar2) // Botón para limpiar
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente2) // Avanza a la siguiente vista.
        val btnAnterior = findViewById<Button>(R.id.btnAtras2) //Regresa a la vista anterior.

        prefs = getSharedPreferences("DatosPersonajes", MODE_PRIVATE)
        // obtiene el archivo de preferencia.

        // Cargar valor guardado
        nombre2.setText(prefs.getString("nombre2", ""))

        //  Botón borrar
        btnBorrar.setOnClickListener {
            nombre2.text.clear()
            // Borra también del almacenamiento
            prefs.edit { remove("nombre2") }
        }

        //  Botón siguiente
        btnSiguiente.setOnClickListener {
            // Guarda el nombre en SharedPreferences
            prefs.edit { putString("nombre2", nombre2.text.toString()) }
            val intent = Intent(this, Personaje3Activity::class.java)
            startActivity(intent)
        }

        // Botón anterior.
        btnAnterior.setOnClickListener {
            //Guarda el nombre en SharedPreferences
            prefs.edit { putString("nombre2", nombre2.text.toString()) }
            val intent = Intent(this, Personaje1Activity::class.java)
            startActivity(intent)
        }
    }

    //  Guarda automáticamente cuando se pause la Activity
    override fun onPause() {
        super.onPause()
        val nombre2 = findViewById<EditText>(R.id.nombre2)
        prefs.edit { putString("nombre2", nombre2.text.toString()) }
    }
}
