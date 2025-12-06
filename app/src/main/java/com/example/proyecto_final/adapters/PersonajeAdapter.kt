package com.example.proyecto_final.adapters

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto_final.R
import com.example.proyecto_final.personaje.Personaje

class PersonajeAdapter(
    private val personajes: MutableList<Personaje>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<PersonajeAdapter.ViewHolder>() {

    /** Listener para que el ViewHolder pueda pedir iniciar el drag */
    private var startDragListener: ((RecyclerView.ViewHolder) -> Unit)? = null

    fun setOnHandleDragListener(listener: (RecyclerView.ViewHolder) -> Unit) {
        startDragListener = listener
    }

    /** ------------------------- VIEW HOLDER ------------------------- */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPersonaje: ImageView = view.findViewById(R.id.imgPersonaje)
        val txtNombre: TextView = view.findViewById(R.id.txtNombre)
        val handleDrag: ImageView = view.findViewById(R.id.handleDrag)
    }

    /** ------------------------- INFLATE ------------------------- */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_personaje_resumen, parent, false)
        return ViewHolder(v)
    }

    /** ------------------------- BIND ------------------------- */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val personaje = personajes[position]

        // Nombre
        holder.txtNombre.text = personaje.nombre.ifBlank { "Personaje ${position + 1}" }

        // Imagen (si tiene, sino default)
        if (personaje.imagen != null) {
            Glide.with(holder.itemView)
                .load(personaje.imagen!!) // ← AQUÍ EL CAMBIO
                .into(holder.imgPersonaje)
        } else {
            holder.imgPersonaje.setImageResource(R.drawable.personaje1)
        }

        // Click normal
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }

        // Handle para arrastrar
        holder.handleDrag.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                startDragListener?.invoke(holder)
            }
            false
        }
    }

    override fun getItemCount(): Int = personajes.size

    /** ------------------------- MOVER / REORDENAR ------------------------- */
    fun moverItem(desde: Int, hacia: Int) {
        val item = personajes.removeAt(desde)
        personajes.add(hacia, item)
        notifyItemMoved(desde, hacia)
    }
}
