package com.antonioselvas.finanzasapp.domain.interfaces

import com.antonioselvas.finanzasapp.domain.models.SplitAccount
import com.antonioselvas.finanzasapp.domain.models.SplitAccountInfo
import com.antonioselvas.finanzasapp.domain.models.SplitAccountTransaction


interface SplitAccountRepository {
    suspend fun addSplitAccount(
        uid: String,
        splitAccountTransaction: SplitAccountTransaction
    ): Result<Unit>

    suspend fun getSplitAccounts(uid: String): List<SplitAccountInfo>

    suspend fun getSplitAccountDetails(id: String, uid: String): SplitAccountTransaction?

    suspend fun updateSplitAccountUser(
        transactionId: String,
        uid: String,
        debtorUserId: String,
        newPaidAmount: Double? = null
    ): Result<Unit>

    suspend fun addUserToSplitAccount(
        uid: String,
        transactionId: String,
        newUser: SplitAccount
    ): Result<Unit>

    suspend fun removeUserFromSplitAccount(
        uid: String,
        transactionId: String,
        debtorUserId: String
    ): Result<Unit>
}