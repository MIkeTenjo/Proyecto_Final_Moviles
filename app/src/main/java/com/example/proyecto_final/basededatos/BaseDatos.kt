package com.example.proyecto_final.basededatos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.proyecto_final.basededatos.datosPersonaje.ConvertidorPersonaje
import com.example.proyecto_final.basededatos.datospartida.Partida
import com.example.proyecto_final.basededatos.datospartida.PartidaDao


@Database(
    entities = [Partida::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ConvertidorPersonaje::class)
abstract class BaseDatos : RoomDatabase() {

    abstract fun partidaDao(): PartidaDao

    companion object {

        @Volatile
        private var INSTANCIA: BaseDatos? = null

        fun getDatabase(context: Context): BaseDatos {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDatos::class.java,
                    "partidas_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCIA = instancia
                instancia
            }
        }
    }
}
