package com.example.actividad2crud

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactos")
data class Contacto(
    @PrimaryKey var numero : String,
    @ColumnInfo (name = "nombre") var nombre : String
)

