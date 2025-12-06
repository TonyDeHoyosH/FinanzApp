package com.antonioselvas.finanzasapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonioselvas.finanzasapp.domain.interfaces.FinanceRepository
import com.antonioselvas.finanzasapp.domain.models.CompleteUserInfo
import com.antonioselvas.finanzasapp.domain.usecases.AddExpenseUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
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

    private val auth: FirebaseAuth = Firebase.auth
    private val fireStore = Firebase.firestore
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private val _userInfo = MutableStateFlow(CompleteUserInfo())
    val userInfo: StateFlow<CompleteUserInfo> = _userInfo


    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {

                val uid = financeRepository.getCurrentUserId()

                if (uid.isNotEmpty()) {

                    val balance = financeRepository.getCurrentBalance(uid)
                    val name = financeRepository.getUserName(uid)
                    val lastExpenses = financeRepository.getLastFiveExpenses(uid)


                    _uiState.value = _uiState.value.copy(
                        currentBalance = balance,
                        isLoading = false,
                        error = null
                    )

                    _userInfo.value = CompleteUserInfo(
                        idUser = uid,
                        name = name,
                        email = FirebaseAuth.getInstance().currentUser?.email ?: "",
                        lastExpens = lastExpenses
                    )
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = "Usuario no identificado")
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    fun addTransaction(amount: Double, description: String, category: String, type: String, date: Long, typeTransaction: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = addExpenseUseCase(
                uid = financeRepository.getCurrentUserId(),
                amount = amount,
                description = description,
                typeTransaction = typeTransaction,
                category = category,
                type = type,
                date = date
            )

            result.fold(
                onSuccess = {
                    loadHomeData()
                    _uiState.value = _uiState.value.copy(
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


}


data class HomeUiState(
    val currentBalance: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null
)
