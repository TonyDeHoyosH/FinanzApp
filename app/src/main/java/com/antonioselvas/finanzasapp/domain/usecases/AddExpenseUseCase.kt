package com.antonioselvas.finanzasapp.domain.usecases

import com.antonioselvas.finanzasapp.domain.interfaces.FinanceRepository
import com.antonioselvas.finanzasapp.domain.models.Transaction
import jakarta.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    suspend operator fun invoke(
        uid: String,
        amount: Double,
        description: String,
        typeTransaction: String,
        category: String,
        type: String,
        date: Long
    ): Result<Unit> {
        val transaction = Transaction(
            amount = amount,
            description = description,
            category = category,
            typeTransaction = typeTransaction,
            type = type,
            date = date,
        )
        return try {
            if (typeTransaction == "Income"){
                return repository.addIncome(uid, transaction)
            }
            val currentBalance = repository.getCurrentBalance(uid)
            if (currentBalance < amount) {
                Result.failure(Exception("Saldo insuficiente"))
            } else {
                repository.addExpense(uid, transaction)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}