package com.antonioselvas.finanzasapp.domain.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalTaxi
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Paid
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.TheaterComedy
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector

data class Category(
    val name: String,
    val icon: ImageVector,
    val amount: Double? = null,
    val color: Color? = null,
    val percentage: Float? = null,

)

data class CategoryAppearance(
    val icon: ImageVector,
    val iconColor: Color,
    val backgroundColor: Color
)

// Definición de colores
val OrangeIcon = Color(0xFFFF6F00)
val LightOrangeBackground = Color(0xFFFFE0B2)

val BlueIcon = Color(0xFF1976D2)
val LightBlueBackground = Color(0xFFBBDEFB)

val GreenIcon = Color(0xFF388E3C)
val LightGreenBackground = Color(0xFFC8E6C9)

val PurpleIcon = Color(0xFF6A1B9A)
val LightPurpleBackground = Color(0xFFE1BEE7)

val RedIcon = Color(0xFFD32F2F)
val LightRedBackground = Color(0xFFFFCDD2)

val TealIcon = Color(0xFF00796B)
val LightTealBackground = Color(0xFFB2DFDB)
