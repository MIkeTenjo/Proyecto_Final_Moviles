package com.example.proyecto_final.personaje.efectos

import com.example.proyecto_final.personaje.Personaje

class EfectoRobarMagia(private val cantidad: Int) : Efecto {
    override fun aplicar(usuario: Personaje, objetivo: Personaje) {
        objetivo.modificarMagia(-cantidad)
        usuario.modificarMagia(cantidad)
    }
}
