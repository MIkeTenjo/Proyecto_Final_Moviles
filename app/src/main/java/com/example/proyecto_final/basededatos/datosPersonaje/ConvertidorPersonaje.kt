package com.example.proyecto_final.basededatos.datosPersonaje

import androidx.room.TypeConverter
import com.example.proyecto_final.personaje.Personaje
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConvertidorPersonaje {

    private val gson = Gson()

    @TypeConverter
    fun personajeToJson(personaje: Personaje?): String {
        return gson.toJson(personaje)
    }

    @TypeConverter
    fun jsonToPersonaje(json: String): Personaje? {
        val type = object : TypeToken<Personaje>() {}.type
        return gson.fromJson(json, type)
    }
}