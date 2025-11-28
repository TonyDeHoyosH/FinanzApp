package com.antonioselvas.finanzasapp.domain.models

data class Expense(
    val id: String = "",
    val amount: Double,
    val description: String,
    val category: String,
    val type: ExpenseType,
    val date: Long,
)

enum class ExpenseType { NORMAL, FIXED }

data class FixedExpense(
    val id: String = "",
    val amount: Double,
    val description: String,
    val frequency: Frequency,
    val chargeDay: Int,
    val category: String,
    val nextChargeDate: Long
)

enum class Frequency { DAILY, WEEKLY, MONTHLY }