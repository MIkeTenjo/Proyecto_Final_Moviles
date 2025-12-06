package com.example.proyecto_final.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto_final.R
import com.example.proyecto_final.adapters.PersonajeAdapter
import com.example.proyecto_final.basededatos.BaseDatos
import com.example.proyecto_final.basededatos.datospartida.Partida
import com.example.proyecto_final.personaje.Personaje
import com.google.android.material.navigation.NavigationView
import kotlin.concurrent.thread

class ResumenActivity : AppCompatActivity() {

    private lateinit var drawer: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toggle: androidx.appcompat.app.ActionBarDrawerToggle

    private lateinit var toolbar: com.google.android.material.appbar.MaterialToolbar
    private lateinit var recycler: RecyclerView
    private lateinit var btnAceptar: Button
    private lateinit var btnCancelar: Button

    private var numeroPartida: Int = -1
    private var partida: Partida? = null
    private val listaOrden = mutableListOf<Personaje>()
    private lateinit var adapter: PersonajeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen)

        drawer = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)
        toolbar = findViewById(R.id.toolbarResumen)

        recycler = findViewById(R.id.recyclerPersonajes)
        btnAceptar = findViewById(R.id.btnAceptar)
        btnCancelar = findViewById(R.id.btnCancelar)

        numeroPartida = intent.getIntExtra("numPartida", -1)

        /** ------------------------------
         *    CONFIGURAR TOOLBAR Y HAMBURGUESA
         * ------------------------------ */
        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        /** ------------------------------
         *   MANEJAR CLICS EN EL NAVIGATIONVIEW
         * ------------------------------ */
        navView.setNavigationItemSelectedListener { item ->
            Toast.makeText(this, "Click: ${item.title}", Toast.LENGTH_SHORT).show()
            drawer.closeDrawers()
            true
        }

        /** ------------------------------ */
        cargarMenuPartidas()
        cargarPartidaDesdeBD()
        /** ------------------------------ */

        btnAceptar.setOnClickListener {
            Toast.makeText(this, "Datos confirmados", Toast.LENGTH_SHORT).show()
            finishAffinity()
        }

        btnCancelar.setOnClickListener {
            startActivity(Intent(this, PartidasActivity::class.java))
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Para que el toggle (hamburguesa) funcione correctamente
        return if (toggle.onOptionsItemSelected(item)) true
        else super.onOptionsItemSelected(item)
    }

    /** ------------------------------
     *     CARGAR MENU LATERAL
     * ------------------------------ */
    private fun cargarMenuPartidas() {
        thread {
            val dao = BaseDatos.getDatabase(this).partidaDao()
            val partidas = dao.obtenerPartidas() // ← ESTE ES TU MÉTODO REAL

            runOnUiThread {
                val menu = navView.menu
                val grupo = menu.findItem(R.id.grupo_partidas)?.subMenu ?: menu.addSubMenu("Partidas")

                grupo.clear()

                // Agregar "Nueva Partida"
                grupo.add("Nueva Partida").setOnMenuItemClickListener {
                    val intent = Intent(this, PartidasActivity::class.java)
                    intent.putExtra("nueva", true)
                    startActivity(intent)
                    true
                }

                // Agregar partidas reales desde ROOM
                partidas.forEach { p ->

                    grupo.add("Partida ${p.numeroPartida} - ${p.nombrePartida}")
                        .setOnMenuItemClickListener {

                            val intent = Intent(this, ResumenActivity::class.java)
                            intent.putExtra("numPartida", p.numeroPartida)
                            startActivity(intent)
                            finish()
                            true
                        }
                }
            }
        }
    }

    /** ------------------------------
     *      CARGAR PARTIDA ACTUAL
     * ------------------------------ */
    private fun cargarPartidaDesdeBD() {
        thread {
            val dao = BaseDatos.getDatabase(this).partidaDao()
            val resultado = dao.obtenerPartida(numeroPartida)
            partida = resultado

            runOnUiThread {
                if (resultado == null) {
                    Toast.makeText(this, "No se encontró la partida.", Toast.LENGTH_LONG).show()
                } else {
                    listaOrden.clear()
                    listaOrden.add(resultado.personaje1)
                    listaOrden.add(resultado.personaje2)
                    listaOrden.add(resultado.personaje3)

                    configurarRecycler()
                }
            }
        }
    }

    private fun configurarRecycler() {

        adapter = PersonajeAdapter(listaOrden) { index ->
            val intent = Intent(this, PersonajeActivity::class.java)
            intent.putExtra("numPartida", numeroPartida)
            intent.putExtra("numPersonaje", index + 1)
            startActivity(intent)
        }

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val from = vh.adapterPosition
                val to = target.adapterPosition
                adapter.moverItem(from, to)
                return true
            }

            override fun onSwiped(vh: RecyclerView.ViewHolder, direction: Int) {}
            override fun isLongPressDragEnabled(): Boolean = false
        }

        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recycler)

        adapter.setOnHandleDragListener { viewHolder ->
            touchHelper.startDrag(viewHolder)
        }
    }
}
