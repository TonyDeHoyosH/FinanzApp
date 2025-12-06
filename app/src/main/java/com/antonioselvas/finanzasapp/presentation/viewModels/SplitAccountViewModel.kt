package com.antonioselvas.finanzasapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonioselvas.finanzasapp.domain.interfaces.FinanceRepository
import com.antonioselvas.finanzasapp.domain.interfaces.SplitAccountRepository
import com.antonioselvas.finanzasapp.domain.models.SplitAccount
import com.antonioselvas.finanzasapp.domain.models.SplitAccountInfo
import com.antonioselvas.finanzasapp.domain.usecases.SplitAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplitAccountViewModel @Inject constructor(
    private val splitAccountUseCase: SplitAccountUseCase,
    private val repository: FinanceRepository,
    private val splitAccountRepository: SplitAccountRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(SplitUiState())
    val uiState: StateFlow<SplitUiState> = _uiState.asStateFlow()

    init {
        loadSplitAccountData()
    }


    fun loadSplitAccountData(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val uuid = repository.getCurrentUserId()

                if (uuid.isNotEmpty()){
                    val splitAccountList = splitAccountRepository.getSplitAccounts(uuid)

                    _uiState.value = _uiState.value.copy(
                        splitAccounts = splitAccountList,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error al cargar gastos compartidos: ${e.message}"
                )
            }
        }
    }

    fun addSplitAccount(
        amount: Double,
        description: String,
        category: String,
        type: String,
        date: Long,
        divisionForm: String,
        users: MutableList<SplitAccount>,
        typeTransaction: String
    ){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = splitAccountUseCase.AddSplitAccount(
                uuid = repository.getCurrentUserId(),
                amount = amount,
                description = description,
                category = category,
                type = type,
                date = date,
                divisionForm = divisionForm,
                users = users,
                typeTransaction = typeTransaction
            )

            result.fold(
                onSuccess = {
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
}

data class SplitUiState(
    val splitAccounts: List<SplitAccountInfo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null
)
