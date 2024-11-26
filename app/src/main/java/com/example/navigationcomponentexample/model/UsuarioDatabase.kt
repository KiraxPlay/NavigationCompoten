package com.example.navigationcomponentexample.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.navigationcomponentexample.model.dao.UserDao
import com.example.navigationcomponentexample.model.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UsuarioDatabase: RoomDatabase() {
    abstract fun getUserDao():UserDao
}