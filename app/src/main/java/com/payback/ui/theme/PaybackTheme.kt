package com.payback.ui.theme

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    primary = Palette.Millbrook,
    onPrimary = Palette.White,
    secondary = Palette.Boulder,
    onSecondary = Palette.Black,
    tertiary = Palette.Boulder,
    onTertiary = Palette.White,
    surface = Palette.SilverChalice,
    onSurface = Palette.Black,
    background = Palette.Fiord,
    onBackground = Palette.White,
    error = Palette.PersianRed,
    onError = Palette.White
)

private val lightColorScheme = lightColorScheme(
    primary = Palette.Cerulean,
    onPrimary = Palette.White,
    secondary = Palette.ElectricViolet,
    onSecondary = Palette.White,
    tertiary = Palette.SilverChalice,
    onTertiary = Palette.Black,
    surface = Palette.WildSand,
    onSurface = Palette.Black,
    background = Palette.White,
    onBackground = Palette.Black,
    error = Palette.Pomegranate,
    onError = Palette.White
)

@Composable
internal fun PaybackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
