package com.example.navigationcomponentexample.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)//creando la llave de primaria
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "nombre") val nombre:String,
    @ColumnInfo(name = "apellido") val apellido: String
)

