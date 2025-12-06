package com.example.proyecto_final.activity

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_final.R
import com.example.proyecto_final.basededatos.BaseDatos
import com.example.proyecto_final.basededatos.datospartida.Partida
import com.example.proyecto_final.personaje.Personaje
import kotlinx.coroutines.*

class PersonajeActivity : AppCompatActivity() {

    private var numeroPartida = 1
    private var numeroPersonaje = 1

    private lateinit var db: BaseDatos

    // UI
    private lateinit var ivImagenClase: ImageView
    private lateinit var tvNombreClase: TextView
    private lateinit var tvDescripcionClase: TextView
    private lateinit var etNombreEditable: EditText

    private lateinit var txtAtaque: TextView
    private lateinit var txtDefensa: TextView
    private lateinit var txtVida: TextView
    private lateinit var txtMagia: TextView
    private lateinit var txtSuerte: TextView

    private lateinit var btnGuardar: Button
    private lateinit var btnSiguiente: Button
    private lateinit var btnAtras: Button

    private var personajeActual: Personaje? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaje)

        db = BaseDatos.getDatabase(this)
        recibirIntent()
        inicializarUI()
        cargarPersonajeDesdeBD()
    }

    private fun recibirIntent() {
        numeroPartida = intent.getIntExtra("numPartida", 1)
        numeroPersonaje = intent.getIntExtra("numPersonaje", 1)
    }

    private fun inicializarUI() {
        ivImagenClase = findViewById(R.id.ivImagenClase)
        tvNombreClase = findViewById(R.id.tvNombreClase)
        tvDescripcionClase = findViewById(R.id.tvDescripcionClase)
        etNombreEditable = findViewById(R.id.etNombreEditable)

        txtAtaque = findViewById(R.id.txtStatAtaque)
        txtDefensa = findViewById(R.id.txtStatDefensa)
        txtVida = findViewById(R.id.txtStatVida)
        txtMagia = findViewById(R.id.txtStatMagia)
        txtSuerte = findViewById(R.id.txtStatSuerte)

        btnGuardar = findViewById(R.id.btnCrearPersonaje)
        btnAtras = findViewById(R.id.btnAtras)
        btnSiguiente = findViewById(R.id.btnSiguiente)

        btnGuardar.setOnClickListener { guardarPersonaje() }

        // --------------------- BOTÓN ATRÁS ---------------------
        btnAtras.setOnClickListener {
            when (numeroPersonaje) {
                1 -> irAResumen()                           // ⬅️ Regresar al resumen si es personaje 1
                else -> abrirPersonaje(numeroPersonaje - 1) // ⬅️ Personaje anterior
            }
        }

        // --------------------- BOTÓN SIGUIENTE ---------------------
        btnSiguiente.setOnClickListener {
            when (numeroPersonaje) {
                3 -> irAResumen()                           // ⬅️ Ir al resumen si es personaje 3
                else -> abrirPersonaje(numeroPersonaje + 1) // ⬅️ Personaje siguiente
            }
        }
    }

    private fun cargarPersonajeDesdeBD() {
        CoroutineScope(Dispatchers.IO).launch {
            val partida = db.partidaDao().obtenerPartida(numeroPartida)

            personajeActual = when (numeroPersonaje) {
                1 -> partida?.personaje1
                2 -> partida?.personaje2
                3 -> partida?.personaje3
                else -> null
            } ?: crearPersonajeVacio()

            withContext(Dispatchers.Main) {
                actualizarVista()
            }
        }
    }

    // -------------------- MOSTRAR DATOS --------------------

    private fun actualizarVista() {
        val p = personajeActual ?: return

        etNombreEditable.setText(p.nombre)

        txtAtaque.text = "Ataque: ${p.ataque}"
        txtDefensa.text = "Defensa: ${p.defensa}"
        txtVida.text = "Vida: ${p.vida}"
        txtMagia.text = "Magia: ${p.magia}"
        txtSuerte.text = "Suerte: ${p.suerte}"

        tvNombreClase.text = obtenerNombreClase(numeroPersonaje)
        tvDescripcionClase.text = obtenerDescripcionClase(numeroPersonaje)
        ivImagenClase.setImageResource(obtenerImagenClase(numeroPersonaje))
    }

    private fun obtenerNombreClase(n: Int): String =
        when (n) {
            1 -> "Robot Virtual"
            2 -> "Asesino"
            3 -> "Esencia Virtual"
            else -> "Personaje"
        }

    private fun obtenerDescripcionClase(n: Int): String =
        when (n) {
            1 -> "Un robot avanzado con protocolos de combate."
            2 -> "Un asesino experto en ataques críticos."
            3 -> "Una entidad digital hecha de pura energía."
            else -> ""
        }

    private fun obtenerImagenClase(n: Int): Int =
        when (n) {
            1 -> R.drawable.personaje1
            2 -> R.drawable.personaje2
            3 -> R.drawable.personaje3
            else -> R.drawable.personaje1
        }

    // -------------------- GUARDAR PERSONAJE --------------------

    private fun guardarPersonaje() {
        val p = personajeActual ?: return

        p.nombre = etNombreEditable.text.toString()
        p.imagen = obtenerImagenClase(numeroPersonaje)

        CoroutineScope(Dispatchers.IO).launch {
            val dao = db.partidaDao()
            val partida = dao.obtenerPartida(numeroPartida)

            val nueva = if (partida != null) {
                partida.copy(
                    personaje1 = if (numeroPersonaje == 1) p else partida.personaje1,
                    personaje2 = if (numeroPersonaje == 2) p else partida.personaje2,
                    personaje3 = if (numeroPersonaje == 3) p else partida.personaje3
                )
            } else {
                crearNuevaPartida(p)
            }

            dao.insertarPartida(nueva)

            withContext(Dispatchers.Main) {
                if (numeroPersonaje == 3) {
                    irAResumen()
                } else {
                    abrirPersonaje(numeroPersonaje + 1)
                }
            }
        }
    }

    private fun abrirPersonaje(nuevoNum: Int) {
        val intent = Intent(this, PersonajeActivity::class.java)
        intent.putExtra("numPartida", numeroPartida)
        intent.putExtra("numPersonaje", nuevoNum)
        startActivity(intent)
        finish()
    }

    private fun irAResumen() {
        val intent = Intent(this, ResumenActivity::class.java)
        intent.putExtra("numPartida", numeroPartida)
        startActivity(intent)
        finish()
    }

    // -------------------- CREACIÓN --------------------

    private fun crearPersonajeVacio(): Personaje {
        return Personaje(
            nombre = "",
            descripcion = "",
            imagen = null,
            nivel = 1,
            ataque = 10,
            defensa = 10,
            vida = 100,
            magia = 50,
            experiencia = 0,
            suerte = 5
        )
    }

    private fun crearNuevaPartida(p: Personaje): Partida {
        return Partida(
            id = numeroPartida,
            numeroPartida = numeroPartida,
            nombrePartida = "Partida $numeroPartida",
            personaje1 = if (numeroPersonaje == 1) p else crearPersonajeVacio(),
            personaje2 = if (numeroPersonaje == 2) p else crearPersonajeVacio(),
            personaje3 = if (numeroPersonaje == 3) p else crearPersonajeVacio()
        )
    }
}

