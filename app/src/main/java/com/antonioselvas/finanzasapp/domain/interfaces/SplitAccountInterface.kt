package com.antonioselvas.finanzasapp.domain.interfaces

import com.antonioselvas.finanzasapp.domain.models.SplitAccountInfo
import com.antonioselvas.finanzasapp.domain.models.SplitAccountTransaction


interface SplitAccountRepository {
    suspend fun addSplitAccount(uid: String, splitAccountTransaction: SplitAccountTransaction): Result<Unit>

    suspend fun getSplitAccounts(uid: String): List<SplitAccountInfo>
}