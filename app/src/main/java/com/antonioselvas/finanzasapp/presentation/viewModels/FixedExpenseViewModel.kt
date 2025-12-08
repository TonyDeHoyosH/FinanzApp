package com.antonioselvas.finanzasapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonioselvas.finanzasapp.domain.models.FixedExpense
import com.antonioselvas.finanzasapp.infrastructure.repository.FixedExpenseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class FixedExpenseViewModel @Inject constructor(
    private val repository: FixedExpenseRepositoryImpl
) : ViewModel() {

    private val _fixedExpenses = MutableStateFlow<List<FixedExpense>>(emptyList())
    val fixedExpenses: StateFlow<List<FixedExpense>> = _fixedExpenses.asStateFlow()

    private val _monthlyTotal = MutableStateFlow(0.0)
    val monthlyTotal: StateFlow<Double> = _monthlyTotal.asStateFlow()

    init {
        loadFixedExpenses()
        checkAndProcessDueExpenses()
    }

    fun loadFixedExpenses() {
        viewModelScope.launch {
            val expenses = repository.getFixedExpenses()
            _fixedExpenses.value = expenses

            _monthlyTotal.value = expenses
                .filter { it.frequency == "Mensual" }
                .sumOf { it.amount }
        }
    }

    companion object {
        fun calculateNextChargeDate(
            lastDate: Long,
            frequency: String,
            chargeDay: Int
        ): Long {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = lastDate

            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            when (frequency) {
                "Diario" -> {
                    calendar.add(Calendar.DAY_OF_YEAR, 1)
                }

                "Semanal" -> {
                    calendar.add(Calendar.WEEK_OF_YEAR, 1)
                }

                "Mensual" -> {
                    val today = Calendar.getInstance()
                    today.set(Calendar.HOUR_OF_DAY, 0)
                    today.set(Calendar.MINUTE, 0)
                    today.set(Calendar.SECOND, 0)
                    today.set(Calendar.MILLISECOND, 0)

                    val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                    val dayToSet = if (chargeDay > maxDay) maxDay else chargeDay
                    calendar.set(Calendar.DAY_OF_MONTH, dayToSet)

                    while (calendar.timeInMillis <= today.timeInMillis) {
                        calendar.add(Calendar.MONTH, 1)
                        val newMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                        val newDayToSet = if (chargeDay > newMaxDay) newMaxDay else chargeDay
                        calendar.set(Calendar.DAY_OF_MONTH, newDayToSet)
                    }
                }

                "Anual" -> {
                    calendar.add(Calendar.YEAR, 1)
                }
            }

            return calendar.timeInMillis
        }
    }

    fun registerFixedExpense(
        amount: String,
        description: String,
        category: String,
        type: String,
        frequency: String,
        chargeDay: String,
        startDate: Long
    ) {
        val parsedAmount = amount.toDoubleOrNull() ?: return
        val parsedChargeDay = chargeDay.toIntOrNull() ?: if (frequency == "Diario") 0 else return

        val nextChargeDate = calculateNextChargeDate(startDate, frequency, parsedChargeDay)

        val newExpense = FixedExpense(
            amount = parsedAmount,
            description = description,
            category = category,
            type = type,
            frequency = frequency,
            chargeDay = parsedChargeDay,
            startDate = startDate,
            nextChargeDate = nextChargeDate
        )

        viewModelScope.launch {
            repository.saveFixedExpense(newExpense)
            loadFixedExpenses()
        }
    }

    fun checkAndProcessDueExpenses() {
        viewModelScope.launch {
            val todayCalendar = Calendar.getInstance()
            todayCalendar.set(Calendar.HOUR_OF_DAY, 23)
            todayCalendar.set(Calendar.MINUTE, 59)
            todayCalendar.set(Calendar.SECOND, 59)
            todayCalendar.set(Calendar.MILLISECOND, 999)
            val todayEndTimestamp = todayCalendar.timeInMillis

            val dueExpenses = repository.getDueFixedExpenses(todayEndTimestamp)

            dueExpenses.forEach { expense ->
                try {
                    repository.processFixedExpense(expense)
                } catch (e: Exception) {
                    println("Fallo al procesar gasto ${expense.id}: ${e.message}")
                }
            }
        }
    }

}