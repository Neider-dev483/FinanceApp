package com.example.financeapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.financeapp.data.model.Expense
import com.example.financeapp.data.model.SharedExpense
import java.util.concurrent.Flow

interface SharedExpenseDao {
    @Dao
    interface SharedExpenseDao {

        // 1. Insertar gasto compartido
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(sharedExpense: SharedExpense)

        // 2. Obtener gastos por usuario
        @Query("SELECT * FROM shared_expenses WHERE creatorUserId = :userId ORDER BY date DESC")
        fun getSharedExpensesByUser(userId: Long): kotlinx.coroutines.flow.Flow<List<SharedExpense>>

        // 3. Obtener gastos NO liquidados
        @Query(" SELECT * FROM shared_expenses WHERE creatorUserId = :userId AND settled = 0")
        fun getUnsettledSharedExpenses(userId: Long): kotlinx.coroutines.flow.Flow<List<SharedExpense>>

        // 4. Actualizar
        @Update
        suspend fun update(sharedExpense: SharedExpense)

        // 5. Eliminar
        @Delete
        suspend fun delete(sharedExpense: SharedExpense)
    }
}