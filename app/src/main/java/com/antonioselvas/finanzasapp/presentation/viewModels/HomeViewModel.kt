package com.antonioselvas.finanzasapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonioselvas.finanzasapp.domain.interfaces.FinanceRepository
import com.antonioselvas.finanzasapp.domain.usecases.AddExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val financeRepository: FinanceRepository

) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadBalance()
    }

    fun addExpense(amount: Double, description: String, category: String, isShared: Boolean) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = addExpenseUseCase(
                uid = financeRepository.getCurrentUserId(),
                amount = amount,
                description = description,
                category = category,
            )
            result.fold(
                onSuccess = {
                    loadBalance()
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = "Gasto agregado correctamente"
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            )
        }
    }

//    fun chargeFixedExpenses() {
//        viewModelScope.launch {
//            chargeFixedExpensesUseCase(getCurrentUserId())
//            loadBalance()
//        }
//    }
//
fun loadBalance() {
        viewModelScope.launch {
            val balance = financeRepository.getCurrentBalance(financeRepository.getCurrentUserId())
            _uiState.value = _uiState.value.copy(currentBalance = balance)
        }
    }
}

data class HomeUiState(
    val currentBalance: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null
)
