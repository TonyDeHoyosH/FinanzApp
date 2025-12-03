package com.antonioselvas.finanzasapp.domain.models

data class User(
    val userId: String,
    val name: String,
    val email: String,
    val onboardingCompleted: Boolean
)


data class CompleteUserInfo(
    val idUser: String = "",
    val name: String = "",
    val email: String ="",
    val lastExpenses: List<Expense> = emptyList()
)