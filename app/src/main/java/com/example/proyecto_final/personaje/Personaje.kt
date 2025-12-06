package com.example.proyecto_final.personaje

import com.example.proyecto_final.personaje.equipo.Armadura
import com.example.proyecto_final.personaje.equipo.Equipo
import com.example.proyecto_final.personaje.equipo.TipoArmadura
import com.example.proyecto_final.personaje.magias.Magia
import com.example.proyecto_final.personaje.poderes.Poder

class Personaje(
    var nombre: String,
    var descripcion: String = "",
    var imagen: Int? = null,
    var nivel: Int = 1,
    var ataque: Int = 10,
    var defensa: Int = 10,
    var vida: Int = 100,
    var magia: Int = 50,
    var experiencia: Int = 0,
    var suerte: Int = 5,
) {
    val magias = mutableListOf<Magia>()
    val poderes = mutableListOf<Poder>()
    val equipo = Equipo()

    fun recibirDanio(cantidad: Int) {
        vida = maxOf(0, vida - cantidad)
    }

    fun modificarAtaque(cantidad: Int) {
        ataque += cantidad
    }

    fun modificarDefensa(cantidad: Int) {
        defensa += cantidad
    }

    fun modificarMagia(cantidad: Int) {
        magia += cantidad
    }

    fun equipar(armadura: Armadura) {
        equipo.equipar(armadura, this)
    }

    fun desequipar(tipo: TipoArmadura) {
        equipo.desequipar(tipo, this)
    }

}
