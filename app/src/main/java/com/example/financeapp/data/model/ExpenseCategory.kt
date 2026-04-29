package com.example.financeapp.data.model

/**
 * ExpenseCategory - enumeracion de categorias de gastos
 */
enum class ExpenseCategory(val displayNameL: String) {
    FOOD("Alimentacion"),
    TRANSPORT("Transporte"),
    ENTRETAIMENT("Entretenimiento"),
    BILLS("Servicios"),
    SHOPPING("Compras"),
    HEALT("Salud"),
    EDUCATION("Educacion"),
    TRAVEL("Viajes"),
    OTHER("Otros")
}