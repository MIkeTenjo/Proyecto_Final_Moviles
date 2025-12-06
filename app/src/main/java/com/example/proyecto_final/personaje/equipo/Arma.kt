package com.example.proyecto_final.personaje.equipo

import com.example.proyecto_final.personaje.Personaje

class Arma(
    nombre: String,
    private val bonusAtaque: Int
) : Armadura(nombre, TipoArmadura.ARMA) {

    override fun aplicar(personaje: Personaje) {
        personaje.modificarAtaque(bonusAtaque)
    }

    override fun remover(personaje: Personaje) {
        personaje.modificarAtaque(-bonusAtaque)
    }
}
