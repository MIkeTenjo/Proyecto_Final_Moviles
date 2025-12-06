package com.example.proyecto_final.basededatos.datospartida

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.proyecto_final.personaje.Personaje
@Entity(tableName = "partidas")
data class Partida(
    @PrimaryKey val id: Int,
    val numeroPartida: Int,
    val nombrePartida: String,
    val personaje1: Personaje,
    val personaje2: Personaje,
    val personaje3: Personaje
)