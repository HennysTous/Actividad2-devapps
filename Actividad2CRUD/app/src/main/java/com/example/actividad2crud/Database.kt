package com.example.actividad2crud

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Contacto::class],
    version = 1
)
abstract class Database: RoomDatabase() {
    abstract fun daoContacto(): DaoContacto
}