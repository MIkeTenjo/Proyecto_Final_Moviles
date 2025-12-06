package com.example.proyecto_final.personaje.efectos

import com.example.proyecto_final.personaje.Personaje

class EfectoDebuffAtaque(private val cantidad: Int) : Efecto {
    override fun aplicar(usuario: Personaje, objetivo: Personaje) {
        objetivo.modificarAtaque(-cantidad)
    }
}
