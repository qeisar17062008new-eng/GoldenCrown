package com.example.goldencrown.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// 👑 GoldenCrow Dark Theme (Palet Mewah: 60% Hitam, 30% Surface, 10% Emas)
val GoldenCrowDarkColors = darkColorScheme(
    primary = GcGold,
    onPrimary = GcBlack,
    secondary = GcGoldLight,
    onSecondary = GcBlack,
    background = GcBlack,
    onBackground = GcCream,
    surface = GcSurface,
    onSurface = GcCream,
    surfaceVariant = GcSurfaceHigh,
    onSurfaceVariant = GcMuted,
    outline = GcOutline,
    error = GcError,
    onError = GcCream
)

// Mode Terang Alternatif
val GoldenCrowLightColors = lightColorScheme(
    primary = GcLightGold,
    onPrimary = GcLightSurface,
    secondary = GcGoldDark,
    onSecondary = GcLightSurface,
    background = GcLightBackground,
    onBackground = GcLightTextPrimary,
    surface = GcLightSurface,
    onSurface = GcLightTextPrimary,
    surfaceVariant = GcLightBackground,
    onSurfaceVariant = GcLightTextSecondary,
    outline = GcLightOutline,
    error = GcError,
    onError = GcLightSurface
)

@Composable
fun GoldenCrownTheme(
    darkTheme: Boolean = true, // Default toko emas mewah: Dark mode
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) GoldenCrowDarkColors else GoldenCrowLightColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = GcBlack.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
