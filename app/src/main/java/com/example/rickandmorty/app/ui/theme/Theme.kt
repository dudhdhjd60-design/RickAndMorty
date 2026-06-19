package com.example.rickandmorty.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = RickGreen,
    onPrimary = DeepSpace,
    primaryContainer = RickGreenDark,
    onPrimaryContainer = OnSurfaceDark,
    secondary = MortyYellow,
    onSecondary = DeepSpace,
    secondaryContainer = MortyYellowDark,
    onSecondaryContainer = OnSurfaceDark,
    tertiary = FavoriteColor,
    onTertiary = DeepSpace,
    background = DeepSpace,
    onBackground = OnSurfaceDark,
    surface = CardDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceDark,
    onSurfaceVariant = VariantDark,
    outline = OutlineDark,
    outlineVariant = OutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = RickGreenDark,
    onPrimary = CardLight,
    primaryContainer = Color(0xFFB3EFF7),
    onPrimaryContainer = OnSurfaceLight,
    secondary = MortyYellowDark,
    onSecondary = CardLight,
    tertiary = FavoriteColor,
    onTertiary = CardLight,
    background = Color(0xFFF5F7FA),
    onBackground = OnSurfaceLight,
    surface = CardLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = Color(0xFFEEF2F8),
    onSurfaceVariant = VariantLight,
    outline = OutlineLight,
    outlineVariant = OutlineLight
)

@Composable
fun RickAndMortyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = RickAndMortyTypography,
        content = content
    )
}
