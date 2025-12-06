package com.example.proyecto_final.personaje.efectos

import com.example.proyecto_final.personaje.Personaje

class EfectoDanio(private val danio: Int) : Efecto {
    override fun aplicar(usuario: Personaje, objetivo: Personaje) {
        objetivo.recibirDanio(danio)
    }
}