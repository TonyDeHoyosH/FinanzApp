package com.antonioselvas.finanzasapp.models

data class User(
    val userId: String,
    val name: String,
    val email: String,
    val onboardingCompleted: Boolean
)
