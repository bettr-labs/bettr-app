package org.example.bettr.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = BettrGreen,
    onPrimary = BettrWhite,
    primaryContainer = BettrGreenLight,
    onPrimaryContainer = BettrGreenDark,
    secondary = BettrBlue,
    onSecondary = BettrBlueDark,
    secondaryContainer = BettrBlueLight,
    onSecondaryContainer = BettrBlueDark,
    background = BettrWhite,
    onBackground = BettrGrayDark,
    surface = BettrWhite,
    onSurface = BettrGrayDark,
    surfaceVariant = BettrGrayLight,
    onSurfaceVariant = BettrGray,
    outline = BettrGray
)

@Composable
fun BettrTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = BettrTypography(),
        content = content
    )
}
