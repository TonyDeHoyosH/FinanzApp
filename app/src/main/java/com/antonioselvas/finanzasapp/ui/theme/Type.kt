package com.antonioselvas.finanzasapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.antonioselvas.finanzasapp.R


val JosefinSans = FontFamily(
    Font(R.font.josefinsans_regular, FontWeight.Normal),
    Font(R.font.josefinsans_bold, FontWeight.Bold),
    Font(R.font.josefinsans_semibold, FontWeight.SemiBold),
)

val Typography = Typography(

    bodyLarge = TextStyle(
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = JosefinSans
    ),
    bodyMedium = TextStyle(
        fontFamily = JosefinSans
    ),

    titleLarge = TextStyle(
        fontFamily = JosefinSans
    ),
    titleSmall = TextStyle(
        fontFamily = JosefinSans
    ),
    titleMedium = TextStyle(
        fontFamily = JosefinSans
    ),

    displayLarge = TextStyle(
        fontFamily = JosefinSans
    ),
    displaySmall = TextStyle(
        fontFamily = JosefinSans
    ),
    displayMedium = TextStyle(
        fontFamily = JosefinSans
    ),
    headlineLarge = TextStyle(
        fontFamily = JosefinSans
    ),
    headlineSmall = TextStyle(
        fontFamily = JosefinSans
    ),
    headlineMedium = TextStyle(
        fontFamily = JosefinSans
    ),

    labelLarge = TextStyle(
        fontFamily = JosefinSans
    ),
    labelSmall = TextStyle(
        fontFamily = JosefinSans
    ),
    labelMedium = TextStyle(
        fontFamily = JosefinSans
    )



)



