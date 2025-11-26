package com.antonioselvas.finanzasapp.models

data class OnboardingData(
    val goal: String = "",
    val initialBalance: Double = 0.0,
    val favoriteCategories: List<String> = emptyList(),
    val fixedExpensesOption: String = "",
    val currentBalance: Double = 0.0
)