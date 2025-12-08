package com.antonioselvas.finanzasapp.domain.interfaces

import com.antonioselvas.finanzasapp.domain.models.FixedExpense

interface FixedExpenseRepository {
    suspend fun saveFixedExpense(expense: FixedExpense)

    suspend fun processFixedExpense(expense: FixedExpense)

    suspend fun getDueFixedExpenses(todayTimestamp: Long): List<FixedExpense>

    suspend fun getFixedExpenses(): List<FixedExpense>
}