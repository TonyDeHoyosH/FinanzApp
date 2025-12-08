package com.antonioselvas.finanzasapp.domain.models

import com.google.firebase.firestore.PropertyName

data class Transaction(
    val id: String = "",
    val amount: Double = 0.0,
    val date: Long = 0L,
    val typeTransaction: String = "",
    val description: String = "",
    val category: String = "",
    val type: String = "",
    val divisionForm: String? = null,
    val users: List<SplitAccount> = emptyList()
){
    var isCompleted: Boolean = false
}


data class FixedExpense(
    val id: String = "",
    val amount: Double,
    val description: String,
    val frequency: Frequency,
    val chargeDay: Int,
    val category: String,
    val nextChargeDate: Long
)


data class SplitAccountTransaction(
    val id: String = "",
    val amount: Double = 0.0,
    val description: String = "",
    val category: String= "",
    val type: String = "",
    val date: Long = 0L,
    val divisionForm: String = "",
    val users: MutableList<SplitAccount> = mutableListOf(),
    val typeTransaction: String = "",
    @get:PropertyName("isCompleted")
    @set:PropertyName("isCompleted")
    var isCompleted: Boolean = false
)
enum class Frequency { DAILY, WEEKLY, MONTHLY }