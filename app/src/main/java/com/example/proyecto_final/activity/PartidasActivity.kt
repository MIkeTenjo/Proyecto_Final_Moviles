package com.example.proyecto_final.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.R
import com.example.proyecto_final.adapters.PartidaAdapter
import com.example.proyecto_final.basededatos.BaseDatos
import com.example.proyecto_final.basededatos.datospartida.PartidaItem
import kotlinx.coroutines.*

class PartidasActivity : AppCompatActivity() {

    private lateinit var db: BaseDatos
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PartidaAdapter
    private val partidasLista = mutableListOf<PartidaItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partidas)

        db = BaseDatos.getDatabase(this)
        recyclerView = findViewById(R.id.recyclerPartidas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cargarPartidas()
    }

    private fun cargarPartidas() {
        CoroutineScope(Dispatchers.IO).launch {
            val listaDB = db.partidaDao().obtenerPartidas()

            partidasLista.clear()

            for (i in 1..3) {
                val partida = listaDB.firstOrNull { it.numeroPartida == i }

                if (partida == null) {
                    partidasLista.add(
                        PartidaItem(i, "Nueva Partida", true)
                    )
                } else {
                    partidasLista.add(
                        PartidaItem(
                            numero = partida.numeroPartida,
                            nombre = "Partida ${partida.numeroPartida}: ${partida.nombrePartida}",
                            esNueva = false
                        )
                    )
                }
            }

            withContext(Dispatchers.Main) {
                adapter = PartidaAdapter(
                    partidasLista,
                    onClick = { item -> abrirPartida(item) },
                    onDelete = { item -> eliminarPartida(item) }
                )
                recyclerView.adapter = adapter
            }
        }
    }

    private fun eliminarPartida(item: PartidaItem) {
        if (item.esNueva) return  // No se puede borrar "Nueva Partida"

        CoroutineScope(Dispatchers.IO).launch {
            val partida = db.partidaDao().obtenerPartida(item.numero)
            if (partida != null) db.partidaDao().borrarPartida(partida)

            withContext(Dispatchers.Main) {
                adapter.eliminarPartida(item)
            }
        }
    }

    private fun abrirPartida(item: PartidaItem) {
        if (item.esNueva) {
            val intent = Intent(this, PersonajeActivity::class.java)
            intent.putExtra("numPartida", item.numero)
            intent.putExtra("numPersonaje", 1)
            startActivity(intent)
        } else {
            val intent = Intent(this, ResumenActivity::class.java)
            intent.putExtra("numPartida", item.numero)
            startActivity(intent)
        }
    }
}
