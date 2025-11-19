package com.antonioselvas.finanzasapp.ui.theme
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import primaryColor
import primaryText
import red
import yellow


private val AppColorTheme = lightColorScheme(
    primary = primaryColor,
    secondary = yellow,
    tertiary = red

)


@Composable
fun FinancesAppTheme(
    content: @Composable () -> Unit
) {
        MaterialTheme(
            colorScheme = AppColorTheme,
            typography = Typography,
            content = content
        )
}


