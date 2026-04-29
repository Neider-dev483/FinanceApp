package com.example.financeapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Expense - Entiedad que representa un gasto en la base de datos
 *
 * Esta clase almacena todos los datos que registra un usuario
 * cada gasto tiene: monto, categoria, descripcion, fecha, etc
 */

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val amount: Double,
    val category: ExpenseCategory,
    val description: String,
    val date: Long = System.currentTimeMillis(),
    val isRecurring: Boolean = false
)
