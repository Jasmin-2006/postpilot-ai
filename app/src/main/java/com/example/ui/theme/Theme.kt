package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryFixedDim,
    onPrimary = OnPrimaryFixed,
    primaryContainer = IndigoPrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = SlateSecondaryContainer,
    onSecondary = OnSecondaryContainer,
    tertiary = TertiaryFixedDim,
    onTertiary = OnTertiary,
    background = InverseSurfaceLight,
    surface = InverseSurfaceLight,
    onBackground = InverseOnSurfaceLight,
    onSurface = InverseOnSurfaceLight,
    surfaceVariant = SurfaceContainerHigh,
    onSurfaceVariant = OnSurfaceVariantLight,
    error = ErrorRed,
    errorContainer = ErrorContainer
)

private val LightColorScheme = lightColorScheme(
    primary = IndigoPrimary,
    onPrimary = OnPrimary,
    primaryContainer = IndigoPrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = SlateSecondary,
    onSecondary = OnSecondary,
    secondaryContainer = SlateSecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = TealTertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TealTertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    background = SurfaceLight,
    surface = SurfaceLight,
    onBackground = OnSurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceContainer,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    error = ErrorRed,
    errorContainer = ErrorContainer,
    onError = OnError,
    onErrorContainer = OnErrorContainer,
    inverseSurface = InverseSurfaceLight,
    inverseOnSurface = InverseOnSurfaceLight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = SurfaceLight.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
