package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = BharatNavy,
    onPrimary = Color.White,
    primaryContainer = BharatNavyLight,
    onPrimaryContainer = Color.White,
    secondary = BharatSaffron,
    onSecondary = Color.White,
    secondaryContainer = BharatSaffronContainer,
    onSecondaryContainer = BharatNavyDark,
    tertiary = BharatEmerald,
    onTertiary = Color.White,
    tertiaryContainer = BharatEmeraldContainer,
    onTertiaryContainer = BharatNavyDark,
    background = BharatBgLight,
    onBackground = BharatTextPrimary,
    surface = BharatSurface,
    onSurface = BharatTextPrimary,
    surfaceVariant = BharatSurfaceVariant,
    onSurfaceVariant = BharatTextSecondary,
    outline = BharatBorder,
    error = BharatError,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF82B1FF),
    onPrimary = BharatNavyDark,
    primaryContainer = BharatNavy,
    onPrimaryContainer = Color.White,
    secondary = BharatSaffronLight,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF5D2400),
    onSecondaryContainer = BharatSaffronContainer,
    tertiary = Color(0xFFA5D6A7),
    onTertiary = Color(0xFF003300),
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF1F5F9),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF475569)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent branding for BharatSeva
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
