package com.example.financeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.data.dao.SharedExpenseDao
import com.example.financeapp.data.database.AppDatabase
import com.example.financeapp.data.model.ExpenseCategory
import com.example.financeapp.data.model.Participant
import com.example.financeapp.data.model.SharedExpense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class SharedExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedExpenseDao = AppDatabase.getDataBase(application).sharedExpenseDao()
    private val _sharedExpenses = MutableStateFlow<List<SharedExpense>>(emptyList())
    val sharedExpense: StateFlow<List<SharedExpense>> = _sharedExpenses
    private val _unsettledExpenses = MutableStateFlow<List<SharedExpense>>(emptyList())
    val unsettledExpense: StateFlow<List<SharedExpense>> = _unsettledExpenses

    fun loadShaderExpense(userId: Long) {
        viewModelScope.launch {
            sharedExpenseDao.getSharedExpensesByUser(userId).collect {
                _sharedExpenses.value = it
            }
        }
    }

    fun loeadunsettledExpeneses(userId: Long) {
        viewModelScope.launch {
            sharedExpenseDao.getUnsettledSharedExpenses(userId).collect {
                _unsettledExpenses.value = it
            }
        }
    }

    fun addSharedExpense(
        userId: Long,
        totalAmount: Double,
        description: String,
        category: ExpenseCategory,
        participant: List<Participant>
    ) {
        viewModelScope.launch {
            val sharedExpense = SharedExpense(
                creatorUserId = userId,
                totalAmount = totalAmount,
                description = description,
                category = category,
                participants = participantToJson(participant)
            )
            sharedExpenseDao.insert(sharedExpense)
        }
    }

    fun markAsSettled(sharedExpense: SharedExpense) {
        viewModelScope.launch {
            sharedExpenseDao.update(sharedExpense.copy(settled = true))
        }
    }

    fun deleteSharedExpense(sharedExpense: SharedExpense) {
        viewModelScope.launch {
            sharedExpenseDao.delete(sharedExpense)
        }
    }

    fun parseParticipant(json: String): List<Participant> {
        val participants = mutableListOf<Participant>()
        val jsonArray = JSONArray(json)
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            participants.add(
                Participant(
                    name = obj.getString("name"),
                    amount = obj.getDouble("amount"),
                    paid = obj.getBoolean("paid")
                )
            )
        }
        return participants
    }

    private fun participantToJson(participants: List<Participant>): String {
        val jsonArray = JSONArray()
        participants.forEach { participants ->
            val obj = JSONObject()
            obj.put("name", participants.name)
            obj.put("amount", participants.amount)
            obj.put("paid", participants.paid)
            jsonArray.put(obj)
        }
        return jsonArray.toString()
    }
}