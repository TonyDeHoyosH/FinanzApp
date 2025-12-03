package com.antonioselvas.finanzasapp.domain.interfaces

import com.antonioselvas.finanzasapp.domain.models.Expense


interface FinanceRepository {
    suspend fun getCurrentBalance(uid: String): Double
    suspend fun getCurrentUserId(): String
    suspend fun addExpense(uid: String, expense: Expense): Result<Unit>

    suspend fun getUserName(uid: String): String

    suspend fun getLastFiveExpenses(uid: String): List<Expense>

    suspend fun getExpensesForChart(uid: String, fromDate: Long, toDate: Long): List<Expense>

}
