package com.example.proyecto_final.personaje.equipo

import com.example.proyecto_final.personaje.Personaje

class Equipo {

    private val equipadas = mutableMapOf<TipoArmadura, Armadura>()

    fun equipar(armadura: Armadura, personaje: Personaje) {
        val tipo = armadura.tipo

        equipadas[tipo]?.remover(personaje)

        equipadas[tipo] = armadura
        armadura.aplicar(personaje)
    }

    fun desequipar(tipo: TipoArmadura, personaje: Personaje) {
        equipadas[tipo]?.let {
            it.remover(personaje)
            equipadas.remove(tipo)
        }
    }
}
