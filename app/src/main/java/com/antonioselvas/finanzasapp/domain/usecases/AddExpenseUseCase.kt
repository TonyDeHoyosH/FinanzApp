package com.antonioselvas.finanzasapp.domain.usecases

import com.antonioselvas.finanzasapp.domain.interfaces.FinanceRepository
import com.antonioselvas.finanzasapp.domain.models.Expense
import com.antonioselvas.finanzasapp.domain.models.ExpenseType
import jakarta.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    suspend operator fun invoke(
        uid: String,
        amount: Double,
        description: String,
        category: String,
    ): Result<Unit> {
        return try {
            val currentBalance = repository.getCurrentBalance(uid)
            if (currentBalance < amount) {
                Result.failure(Exception("Saldo insuficiente"))
            } else {
                val expense = Expense(
                    amount = amount,
                    description = description,
                    category = category,
                    type = ExpenseType.NORMAL,
                    date = System.currentTimeMillis(),
                )
                repository.addExpense(uid, expense)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}