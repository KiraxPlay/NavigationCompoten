package com.example.navigationcomponentexample.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.navigationcomponentexample.model.entities.UserEntity

@Dao
interface UserDao {
    // Leer todos los usuarios (Read)
    @Query("SELECT * FROM usuarios ORDER BY nombre DESC")
    suspend fun getAllUsers(): List<UserEntity>

    // Insertar múltiples usuarios (Create)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(usuarios: List<UserEntity>)

    // Insertar un solo usuario (Create)
    @Insert
    suspend fun insertar(usuario: UserEntity)

    // Actualizar un usuario existente (Update)
    @Update
    suspend fun actualizar(usuario: UserEntity)

    // Eliminar un usuario específico (Delete)
    @Delete
    suspend fun eliminarUsuario(usuario: UserEntity)

    // Eliminar todos los usuarios (Delete)
    @Query("DELETE FROM usuarios")
    suspend fun eliminarTodos()

    // Buscar usuario por ID (Read)
    @Query("SELECT * FROM usuarios WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Int): UserEntity?
}
