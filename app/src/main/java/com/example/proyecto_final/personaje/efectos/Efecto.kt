package com.example.proyecto_final.personaje.efectos

import com.example.proyecto_final.personaje.Personaje

fun interface Efecto {
    fun aplicar(usuario: Personaje, objetivo: Personaje)
}