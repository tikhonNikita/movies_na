package com.movieslist.ui.compose.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val ThanosLightColors = lightColorScheme(
    primary = Color(0xFF6750A4),       // Deep purple
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFE9DDFF),
    onPrimaryContainer = Color(0xFF22005D),

    secondary = Color(0xFF7A6A00),     // Dark gold
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFE16A),
    onSecondaryContainer = Color(0xFF251F00),

    tertiary = Color(0xFF8C4A8E),      // Accent purple
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFD6F9),
    onTertiaryContainer = Color(0xFF390039),

    background = Color(0xFFFEFBFF),
    onBackground = Color(0xFF1B1B1F),

    surface = Color(0xFFFEFBFF),
    onSurface = Color(0xFF1B1B1F),

    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454E),

    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF)
)

private val ThanosDarkColors = darkColorScheme(
    primary = Color(0xFFCFBCFF),       // Light purple
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFE9DDFF),

    secondary = Color(0xFFFFE16A),     // Vibrant gold
    onSecondary = Color(0xFF3E3500),
    secondaryContainer = Color(0xFF594E00),
    onSecondaryContainer = Color(0xFFFFE16A),

    tertiary = Color(0xFFF5ABFF),      // Light accent
    onTertiary = Color(0xFF541A5D),
    tertiaryContainer = Color(0xFF6E3376),
    onTertiaryContainer = Color(0xFFFFD6F9),

    background = Color(0xFF1B1B1F),
    onBackground = Color(0xFFE4E1E6),

    surface = Color(0xFF1B1B1F),
    onSurface = Color(0xFFE4E1E6),

    surfaceVariant = Color(0xFF49454E),
    onSurfaceVariant = Color(0xFFCAC4CF),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

private val ThanosTypography = Typography(
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    )
)


@Composable
fun MoviesListTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) ThanosDarkColors else ThanosLightColors

    MaterialTheme(
        colorScheme = colors,
        typography = ThanosTypography,
        content = content
    )
}