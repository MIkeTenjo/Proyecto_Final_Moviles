package com.example.proyecto_final.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.R
import com.example.proyecto_final.basededatos.datospartida.PartidaItem

class PartidaAdapter(
    private val partidas: MutableList<PartidaItem>,
    private val onClick: (PartidaItem) -> Unit,
    private val onDelete: (PartidaItem) -> Unit
) : RecyclerView.Adapter<PartidaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val texto: TextView = view.findViewById(R.id.textoPartida)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_partida, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val partida = partidas[position]

        holder.texto.text = partida.nombre

        holder.itemView.setOnClickListener {
            onClick(partida)
        }

        holder.btnEliminar.setOnClickListener {
            onDelete(partida)
        }
    }

    override fun getItemCount(): Int = partidas.size

    fun eliminarPartida(item: PartidaItem) {
        val index = partidas.indexOf(item)
        if (index != -1) {
            partidas.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}

