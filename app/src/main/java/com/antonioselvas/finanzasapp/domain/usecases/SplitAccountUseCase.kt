package com.antonioselvas.finanzasapp.domain.usecases

import com.antonioselvas.finanzasapp.domain.interfaces.SplitAccountRepository
import com.antonioselvas.finanzasapp.domain.models.SplitAccountTransaction
import com.antonioselvas.finanzasapp.domain.models.SplitAccount
import javax.inject.Inject

class SplitAccountUseCase @Inject constructor(
    private val splitAccountRepository: SplitAccountRepository
) {

    suspend fun AddSplitAccount(
        uuid: String,
        amount: Double,
        description: String,
        category: String,
        type: String,
        date: Long,
        divisionForm: String,
        users: MutableList<SplitAccount>,
        typeTransaction: String
    ): Result<Unit> {
        val transaction = SplitAccountTransaction(
            amount = amount,
            description = description,
            category = category,
            type = type,
            date = date,
            divisionForm = divisionForm,
            users = users,
            typeTransaction = typeTransaction
        )
        return try {
            if (typeTransaction == "expense"){
                return splitAccountRepository.addSplitAccount(
                    uid = uuid,
                    splitAccountTransaction = transaction
                )
            }

            return Result.failure(IllegalArgumentException("Tipo de transacción no soportado para AddSplitAccount: $typeTransaction"))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}