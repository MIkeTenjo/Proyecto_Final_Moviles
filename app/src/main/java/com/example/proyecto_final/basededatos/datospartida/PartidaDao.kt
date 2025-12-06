package com.example.proyecto_final.basededatos.datospartida
import androidx.room.*

@Dao
interface PartidaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertarPartida(partida: Partida)

    @Query("SELECT * FROM partidas")
    fun obtenerPartidas(): List<Partida>

    @Query("SELECT * FROM partidas WHERE numeroPartida = :num LIMIT 1")
    fun obtenerPartida(num: Int): Partida?

    @Delete
    fun borrarPartida(partida: Partida)
}