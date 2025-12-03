package com.antonioselvas.finanzasapp.domain.interfaces

import com.antonioselvas.finanzasapp.domain.models.Transaction


interface FinanceRepository {
    suspend fun getCurrentBalance(uid: String): Double
    suspend fun getCurrentUserId(): String
    suspend fun addExpense(uid: String, transaction: Transaction): Result<Unit>

    suspend fun getUserName(uid: String): String

    suspend fun getLastFiveExpenses(uid: String): List<Transaction>

    suspend fun getExpensesForChart(uid: String, fromDate: Long, toDate: Long): List<Transaction>

    suspend fun addIncome(uid: String, transaction: Transaction): Result<Unit>
//    suspend fun chargeFixedExpenses(uid: String): Result<Int>
//    suspend fun getFixedExpenses(uid: String): List<FixedExpense>
}
