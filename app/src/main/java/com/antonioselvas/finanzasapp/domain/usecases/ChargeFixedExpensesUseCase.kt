package com.antonioselvas.finanzasapp.domain.usecases

import com.antonioselvas.finanzasapp.domain.interfaces.FinanceRepository
import com.antonioselvas.finanzasapp.domain.models.Expense
import com.antonioselvas.finanzasapp.domain.models.ExpenseType
import com.antonioselvas.finanzasapp.domain.models.FixedExpense
import com.antonioselvas.finanzasapp.domain.models.Frequency
//
//class ChargeFixedExpensesUseCase(private val repository: FinanceRepository) {
//    suspend operator fun invoke(uid: String): Result<Int> {
//        val fixedExpenses = repository.getFixedExpenses(uid)
//        var totalCharged = 0
//
//        fixedExpenses.forEach { fixedExpense ->
//            if (shouldChargeToday(fixedExpense)) {
//                val expense = Expense(
//                    amount = fixedExpense.amount,
//                    description = "${fixedExpense.description} (fijo)",
//                    category = fixedExpense.category,
//                    type = ExpenseType.FIXED,
//                    date = System.currentTimeMillis()
//                )
//                repository.addExpense(uid, expense)
//                totalCharged += fixedExpense.amount
//                // Actualizar próxima fecha de cobro
//                updateNextChargeDate(fixedExpense)
//            }
//        }
//        return Result.success(totalCharged)
//    }
//
//    private fun shouldChargeToday(fixedExpense: FixedExpense): Boolean {
//        val today = getCurrentDate()
//        // Lógica según frecuencia y día de cobro
//        return when (fixedExpense.frequency) {
//            Frequency.MONTHLY -> today.dayOfMonth == fixedExpense.chargeDay
//            Frequency.WEEKLY -> today.dayOfWeek.value == fixedExpense.chargeDay
//            else -> false
//        }
//    }
//}