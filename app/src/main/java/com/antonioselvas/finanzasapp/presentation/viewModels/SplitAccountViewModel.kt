package com.antonioselvas.finanzasapp.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonioselvas.finanzasapp.domain.interfaces.FinanceRepository
import com.antonioselvas.finanzasapp.domain.interfaces.SplitAccountRepository
import com.antonioselvas.finanzasapp.domain.models.SplitAccount
import com.antonioselvas.finanzasapp.domain.models.SplitAccountInfo
import com.antonioselvas.finanzasapp.domain.models.SplitAccountTransaction
import com.antonioselvas.finanzasapp.domain.usecases.SplitAccountUseCase
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.SplitFilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplitAccountViewModel @Inject constructor(
    private val splitAccountUseCase: SplitAccountUseCase,
    private val repository: FinanceRepository,
    private val splitAccountRepository: SplitAccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplitUiState())
    val uiState: StateFlow<SplitUiState> = _uiState.asStateFlow()

    private val _uiStateDetails = MutableStateFlow(SplitUiStateDetails())

    val uiStateDetails: StateFlow<SplitUiStateDetails> = _uiStateDetails.asStateFlow()

    private val _filterType = MutableStateFlow(SplitFilterType.PENDING)
    val filterType: StateFlow<SplitFilterType> = _filterType.asStateFlow()

    fun setFilter(type: SplitFilterType) {
        _filterType.value = type
    }

    val filteredSplitAccounts: StateFlow<List<SplitAccountInfo>> =
        _uiState.combine(_filterType) { uiState, filter ->

            Log.d("SplitFilterDebug", "Filtro actual: $filter")

            uiState.splitAccounts.forEach {
                // Esto imprimirá el estado de CADA ítem que se recibe de Firebase
                Log.d("SplitFilterDebug", "Item ID: ${it.id}, isCompleted: ${it.isCompleted}")
            }

            // La lógica de filtrado sigue igual
            uiState.splitAccounts.filter { splitAccount ->
                when (filter) {
                    SplitFilterType.PENDING -> !splitAccount.isCompleted
                    SplitFilterType.COMPLETED -> splitAccount.isCompleted
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        loadSplitAccountData()
    }


    fun loadSplitAccountData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val uuid = repository.getCurrentUserId()

                if (uuid.isNotEmpty()) {
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
    ) {
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
                    loadSplitAccountData()
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

    fun getSplitAccountDetails(
        id: String
    ) {
        viewModelScope.launch {
            _uiStateDetails.value = _uiStateDetails.value.copy(isLoading = true)

            try {
                val uuid = repository.getCurrentUserId()

                if (uuid.isNotEmpty()) {
                    val result = splitAccountRepository.getSplitAccountDetails(id, uuid)

                    _uiStateDetails.value = _uiStateDetails.value.copy(
                        splitAccount = result,
                        isLoading = false
                    )
                }


            } catch (e: Exception) {
                _uiStateDetails.value = _uiStateDetails.value.copy(
                    isLoading = false,
                    error = "Error al cargar gasto compartido: ${e.message}"
                )
            }
        }
    }

    fun markUserAsPaid(transactionId: String, debtorUserId: String) {
        viewModelScope.launch {
            val uuid = repository.getCurrentUserId()

            val result = splitAccountRepository.updateSplitAccountUser(
                transactionId,
                uuid,
                debtorUserId,
            )
            getSplitAccountDetails(transactionId)
            if (result.isSuccess) {
                loadSplitAccountData()
            }
        }

    }

    fun addUserToSplitAccount(transactionId: String, newUser: SplitAccount) {
        viewModelScope.launch {
            val uuid = repository.getCurrentUserId()

            _uiStateDetails.value = _uiStateDetails.value.copy(isLoading = true, error = null)

            val result = splitAccountRepository.addUserToSplitAccount(
                uid = uuid,
                transactionId = transactionId,
                newUser = newUser
            )

            if (result.isSuccess) {
                _uiStateDetails.value = _uiStateDetails.value.copy(
                    message = "Usuario agregado y gasto actualizado."
                )
                getSplitAccountDetails(transactionId)
                loadSplitAccountData()
            } else {
                _uiStateDetails.value = _uiStateDetails.value.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "Error al agregar usuario."
                )
            }
        }
    }

    fun removeUserFromSplitAccount(transactionId: String, debtorUserId: String) {
        viewModelScope.launch {
            val uuid = repository.getCurrentUserId()

            _uiStateDetails.value = _uiStateDetails.value.copy(isLoading = true, error = null)

            val result = splitAccountRepository.removeUserFromSplitAccount(
                uid = uuid,
                transactionId = transactionId,
                debtorUserId = debtorUserId
            )

            if (result.isSuccess) {
                _uiStateDetails.value = _uiStateDetails.value.copy(
                    message = "Usuario eliminado y gasto actualizado."
                )
                getSplitAccountDetails(transactionId)
                loadSplitAccountData()
            } else {
                _uiStateDetails.value = _uiStateDetails.value.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "Error al eliminar usuario."
                )
            }
        }
    }

}

data class SplitUiState(
    val splitAccounts: List<SplitAccountInfo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null
)

data class SplitUiStateDetails(
    val splitAccount: SplitAccountTransaction? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null
)
