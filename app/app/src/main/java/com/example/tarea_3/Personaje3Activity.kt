package com.example.tarea_3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.core.content.edit

class Personaje3Activity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    //Variable de preferencias compartidas para guardar los datos.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaje3)

        val nombre3 = findViewById<EditText>(R.id.nombre3) //Campo de texto del personaje.
        val btnBorrar = findViewById<Button>(R.id.btnBorrar3) //Botón para limpiar.
        val btnAnterior = findViewById<Button>(R.id.btnAtras3) //Vuelve a la vista anterior.
        val btnResumen = findViewById<Button>(R.id.btnSiguiente3)//Avanza a la vista siguiente.

        prefs = getSharedPreferences("DatosPersonajes", MODE_PRIVATE)
        // obtiene el archivo de preferencia.

        // Cargar valor guardado
        nombre3.setText(prefs.getString("nombre3", ""))

        //  Botón borrar
        btnBorrar.setOnClickListener {
            nombre3.text.clear()
            // Borra también del almacenamiento
            prefs.edit { remove("nombre3") }
        }

        //  Botón Anterior.
        btnAnterior.setOnClickListener {
            //Guarda el nombre en SharedPreferences
            prefs.edit { putString("nombre3", nombre3.text.toString())}
            val intent = Intent(this, Personaje2Activity::class.java)
            startActivity(intent)
        }

        //  Botón siguiente
        btnResumen.setOnClickListener {
            // Guarda el nombre en SharedPreferences
            prefs.edit { putString("nombre3", nombre3.text.toString()) }
            val intent = Intent(this, ResumenActivity::class.java)
            startActivity(intent)
        }
    }

    //  Guarda automáticamente cuando se pause la Activity
    override fun onPause() {
        super.onPause()
        val nombre3 = findViewById<EditText>(R.id.nombre3)
        prefs.edit { putString("nombre3", nombre3.text.toString()) }
    }
}
