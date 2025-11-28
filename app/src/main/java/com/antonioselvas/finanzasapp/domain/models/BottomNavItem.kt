package com.antonioselvas.finanzasapp.domain.models

import android.graphics.drawable.Icon
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val description: String,
    val label: String
)