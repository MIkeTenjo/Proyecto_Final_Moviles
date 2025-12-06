package com.example.proyecto_final.personaje.equipo

import com.example.proyecto_final.personaje.Personaje

interface ModificadorAtributos {
    fun aplicar(personaje: Personaje)
    fun remover(personaje: Personaje)
}