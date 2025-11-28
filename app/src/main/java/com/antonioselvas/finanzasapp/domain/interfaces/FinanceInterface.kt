package com.antonioselvas.finanzasapp.domain.interfaces

import com.antonioselvas.finanzasapp.domain.models.Expense
import com.antonioselvas.finanzasapp.domain.models.FixedExpense


interface FinanceRepository {
    suspend fun getCurrentBalance(uid: String): Double
    suspend fun getCurrentUserId(): String
    suspend fun addExpense(uid: String, expense: Expense): Result<Unit>

    suspend fun getExpensesForChart(uid: String, fromDate: Long, toDate: Long): List<Expense>
//    suspend fun getFinanceSummary(uid: String): FinanceSummary
//    suspend fun addIncome(uid: String, amount: Double, description: String): Result<Unit>
//    suspend fun chargeFixedExpenses(uid: String): Result<Int>
//    suspend fun getFixedExpenses(uid: String): List<FixedExpense>
}
