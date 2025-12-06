package com.antonioselvas.finanzasapp.domain.models


data class SplitAccount(
    val id: String = "",
    val name: String = "",
    val amount: Float= 0.0f,
    var paidAmount: Float = 0.0f,
    var paid: Boolean = false,
    var deleted: Boolean = false
)
data class SplitAccountInfo(
    val id: String = "",
    val amount: Double = 0.0,
    val description: String = "",
    val category: String= "",
    val type: String = "",
    val date: Long = 0L,
    val divisionForm: String = "",
    val usersNumber: Int = 0,
    val typeTransaction: String = ""
)