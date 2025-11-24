package com.antonioselvas.finanzasapp.models

import java.time.temporal.TemporalAmount


data class SplitAccountUser(
    val id: String,
    val name: String,
    val amount: Float,
    var paidAmount: Float,
    var paid: Boolean = false,
    var deleted: Boolean = false
)