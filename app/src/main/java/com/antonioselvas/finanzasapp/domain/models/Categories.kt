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
    val icon: ImageVector
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

val YellowIcon = Color(0xFFAFB42B)
val LightYellowBackground = Color(0xFFF0F4C3)

val PinkIcon = Color(0xFFC2185B)
val LightPinkBackground = Color(0xFFF8BBD0)


val CategoryMap: Map<String, CategoryAppearance>
    get() = mapOf(
        "Alimentación" to CategoryAppearance(
            icon = Icons.Outlined.Fastfood,
            iconColor = OrangeIcon,
            backgroundColor = LightOrangeBackground
        ),
        "Transporte" to CategoryAppearance(
            icon = Icons.Outlined.LocalTaxi,
            iconColor = BlueIcon,
            backgroundColor = LightBlueBackground
        ),
        "Hogar" to CategoryAppearance(
            icon = Icons.Outlined.Home,
            iconColor = GreenIcon,
            backgroundColor = LightGreenBackground
        ),
        "Servicio publico" to CategoryAppearance(
            icon = Icons.Outlined.Paid,
            iconColor = PurpleIcon,
            backgroundColor = LightPurpleBackground
        ),
        "Ropa" to CategoryAppearance(
            icon = Icons.Outlined.ShoppingBag,
            iconColor = PinkIcon,
            backgroundColor = LightPinkBackground
        ),
        "Salud" to CategoryAppearance(
            icon = Icons.Outlined.HealthAndSafety,
            iconColor = RedIcon,
            backgroundColor = LightRedBackground
        ),
        "Educación" to CategoryAppearance(
            icon = Icons.Outlined.School,
            iconColor = TealIcon,
            backgroundColor = LightTealBackground
        ),
        "Entretenimiento" to CategoryAppearance(
            icon = Icons.Outlined.TheaterComedy,
            iconColor = YellowIcon,
            backgroundColor = LightYellowBackground
        ),
        "Mascotas" to CategoryAppearance(
            icon = Icons.Outlined.Pets,
            iconColor = BrownIcon,
            backgroundColor = LightBrownBackground
        ),
        "Otros" to CategoryAppearance(
            icon = Icons.Outlined.MoreHoriz,
            iconColor = Color.Gray,
            backgroundColor = Color(0xFFEEEEEE) // Gris muy claro
        )
    )

// Añade los colores que faltan (ej. para Mascotas)
val BrownIcon = Color(0xFF795548)
val LightBrownBackground = Color(0xFFD7CCC8)