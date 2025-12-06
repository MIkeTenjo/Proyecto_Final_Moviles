package com.example.proyecto_final.personaje.poderes

import com.example.proyecto_final.personaje.efectos.Efecto
import com.example.proyecto_final.personaje.Personaje

class Poder(
    val nombre: String,
    val descripcion: String,
    val costo: Int,
    private val efecto: Efecto
) {
    fun usar(usuario: Personaje, objetivo: Personaje) {
        if (usuario.magia >= costo) {
            usuario.modificarMagia(-costo)
            efecto.aplicar(usuario, objetivo)
        }
    }
}