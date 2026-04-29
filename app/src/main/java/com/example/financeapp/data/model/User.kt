package com.example.financeapp.data.model

import android.R
import android.view.displayhash.DisplayHash
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * User - Entidad que representa un usuario en la base de datos
 *
 *@Entity = Anotacion de Room que va a marcar esta clase como una TABLA de SQLite
 */

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String,
    val passwordHash: String,
    val createAt: Long = System.currentTimeMillis()
)
