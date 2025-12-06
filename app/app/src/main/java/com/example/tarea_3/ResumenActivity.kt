package com.example.tarea_3

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import java.util.*

class ResumenActivity : AppCompatActivity() {

    private lateinit var contenedorPersonajes: LinearLayout
    private val personajes = mutableListOf<Personaje>()

    // `contenedorPersonajes` mostrará dinámicamente los personajes en pantalla.
    // `personajes` almacena los datos de los tres personajes que se van a mostrar.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen)
        contenedorPersonajes = findViewById(R.id.contenedorPersonajes)
        val btnAceptar = findViewById<Button>(R.id.btnAceptar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        // Asigna el diseño correspondiente a esta pantalla (`activity_resumen.xml`).

        // Recuperar los nombres desde SharedPreferences
        val prefs = getSharedPreferences("DatosPersonajes", MODE_PRIVATE)
        val nombre1 = prefs.getString("nombre1", getString(R.string.personaje_1)) ?: ""
        val nombre2 = prefs.getString("nombre2", getString(R.string.personaje_2)) ?: ""
        val nombre3 = prefs.getString("nombre3", getString(R.string.personaje_3)) ?: ""

        personajes.addAll(
            listOf(
                Personaje("Personaje 1", nombre1, R.drawable.personaje1, Personaje1Activity::class.java),
                Personaje("Personaje 2", nombre2, R.drawable.personaje2, Personaje2Activity::class.java),
                Personaje("Personaje 3", nombre3, R.drawable.personaje3, Personaje3Activity::class.java)
            )
        )

        // Crea una lista con tres objetos `Personaje`, cada uno con su nombre original,
        // nombre personalizado (introducido por el usuario), imagen y clase de actividad asociada.


        mostrarPersonajes()

        btnAceptar.setOnClickListener {
            Toast.makeText(this, "Datos confirmados ", Toast.LENGTH_SHORT).show()
        }

        btnCancelar.setOnClickListener {
            finish() // volver atrás
        }

        // Muestra los personajes en pantalla llamando a `mostrarPersonajes()`.
        // El botón “Aceptar” muestra un mensaje de confirmación.
        // El botón “Cancelar” cierra la actividad y regresa a la anterior.

    }

    private fun mostrarPersonajes() {
        contenedorPersonajes.removeAllViews()
        // Limpia el contenedor antes de volver a llenar la lista de personajes.
        // Esto permite actualizar la vista al reordenar o editar personajes.


        personajes.forEachIndexed { index, personaje ->
            val item = layoutInflater.inflate(R.layout.item_personaje_resumen, contenedorPersonajes, false)

            val img = item.findViewById<ImageView>(R.id.imgPersonaje)
            val txt = item.findViewById<TextView>(R.id.txtNombre)

            img.setImageResource(personaje.imagen)
            txt.text = personaje.nombreUsuario
            // Recorre cada personaje de la lista y genera dinámicamente un elemento visual (item)
            // basado en el diseño `item_personaje_resumen.xml`.


            // Tocar para editar
            item.setOnClickListener {
                val intent = Intent(this, personaje.actividadDestino)
                startActivity(intent)
            }

            // Mantener presionado para mover
            item.setOnLongClickListener {
                if (index < personajes.size - 1) {
                    Collections.swap(personajes, index, index + 1)
                } else {
                    Collections.swap(personajes, index, 0)
                }
                mostrarPersonajes()
                true
            }

            contenedorPersonajes.addView(item)
        }
    }

    data class Personaje(
        // Clase interna `Personaje`: define la estructura de cada personaje con su nombre original,
        // el nombre personalizado del usuario, la imagen asociada y la actividad destino
        // (pantalla donde se puede editar).

        val nombreOriginal: String,
        val nombreUsuario: String,
        val imagen: Int,
        val actividadDestino: Class<*>
    )
}

