package com.example.navigationcomponentexample.model.providers

import android.content.Context
import androidx.room.Room
import com.example.navigationcomponentexample.model.UsuarioDatabase

object UserDatabaseProvider {
    fun getDatabase(context: Context): UsuarioDatabase{
        return Room.databaseBuilder(
            context.applicationContext,//Se usa para evitar fugas de memoria
            UsuarioDatabase::class.java,
            "app_BD"
        ).build()
    }
}