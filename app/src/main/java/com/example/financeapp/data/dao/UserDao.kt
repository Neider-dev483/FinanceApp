package com.example.financeapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.financeapp.data.model.User
import kotlinx.coroutines.flow.Flow

/**8
 * userDao - Data Access Object para la tabla Users
 *
 * DAO = Data Access Object (Objeto de acceso a Datos)
 * Es una INTERFAZ que define todas las operaciones que dfine todas
 * las operaciones que puedos hacer sobre la tabla
 *
 * @Dao = Anotacion que marca esta interfaz como DAO
 * Room genera autoamticamente la implementacion de todos los metodos
 *
 */

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE email=:email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Long): Flow<User?>

    @Update
    suspend fun update(user: User)
}