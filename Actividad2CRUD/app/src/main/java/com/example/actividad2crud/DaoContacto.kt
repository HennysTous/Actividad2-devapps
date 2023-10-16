package com.example.actividad2crud

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoContacto {

    @Query("SELECT * FROM contactos")
    suspend fun obtenerContactos():MutableList<Contacto>

    @Insert
    suspend fun agregarContacto(contacto: Contacto)

    @Query("UPDATE contactos set nombre =:nombre WHERE numero=:numero")
    suspend fun actualizarContacto(nombre: String, numero: String)

    @Query("DELETE FROM contactos WHERE numero=:numero")
    suspend fun borrarContacto(numero: String)
}