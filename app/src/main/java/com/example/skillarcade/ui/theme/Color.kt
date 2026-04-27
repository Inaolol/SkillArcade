package com.example.skillarcade.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ─── Arcade brand colors (non-Material3) ────────────────────────────────────

object ArcadeColors {
    val InkBlack = Color(0xFF121212)
    val PrimaryYellow = Color(0xFFFFD500)
    val AccentPink = Color(0xFFAC2A5E)
    val AccentMint = Color(0xFF006C46)
    val Cream = Color(0xFFFFF8EF)
}

// ─── Raw color values ────────────────────────────────────────────────────────

// Primary
private val md_primary = Color(0xFF705D00)
private val md_onPrimary = Color(0xFFFFFFFF)
private val md_primaryContainer = Color(0xFFFFD500)
private val md_onPrimaryContainer = Color(0xFF705C00)
private val md_inversePrimary = Color(0xFFEAC300)

// Primary fixed
private val md_primaryFixed = Color(0xFFFFE174)
private val md_primaryFixedDim = Color(0xFFEAC300)
private val md_onPrimaryFixed = Color(0xFF221B00)
private val md_onPrimaryFixedVariant = Color(0xFF554500)

// Secondary
private val md_secondary = Color(0xFFAC2A5E)
private val md_onSecondary = Color(0xFFFFFFFF)
private val md_secondaryContainer = Color(0xFFFE6B9E)
private val md_onSecondaryContainer = Color(0xFF6E0035)

// Secondary fixed
private val md_secondaryFixed = Color(0xFFFFD9E1)
private val md_secondaryFixedDim = Color(0xFFFFB1C6)
private val md_onSecondaryFixed = Color(0xFF3F001B)
private val md_onSecondaryFixedVariant = Color(0xFF8C0A46)

// Tertiary
private val md_tertiary = Color(0xFF006C46)
private val md_onTertiary = Color(0xFFFFFFFF)
private val md_tertiaryContainer = Color(0xFF32F5A7)
private val md_onTertiaryContainer = Color(0xFF006C46)

// Tertiary fixed
private val md_tertiaryFixed = Color(0xFF4DFFB2)
private val md_tertiaryFixedDim = Color(0xFF00E297)
private val md_onTertiaryFixed = Color(0xFF002112)
private val md_onTertiaryFixedVariant = Color(0xFF005234)

// Error
private val md_error = Color(0xFFBA1A1A)
private val md_onError = Color(0xFFFFFFFF)
private val md_errorContainer = Color(0xFFFFDAD6)
private val md_onErrorContainer = Color(0xFF93000A)

// Background / Surface
private val md_background = Color(0xFFFFF8EF)
private val md_onBackground = Color(0xFF1F1B10)
private val md_surface = Color(0xFFFFF8EF)
private val md_onSurface = Color(0xFF1F1B10)
private val md_surfaceVariant = Color(0xFFEAE2CF)
private val md_onSurfaceVariant = Color(0xFF4D4632)
private val md_surfaceTint = Color(0xFF705D00)
private val md_inverseSurface = Color(0xFF343024)
private val md_inverseOnSurface = Color(0xFFF9F0DD)

// Surface containers
private val md_surfaceDim = Color(0xFFE2D9C7)
private val md_surfaceBright = Color(0xFFFFF8EF)
private val md_surfaceContainerLowest = Color(0xFFFFFFFF)
private val md_surfaceContainerLow = Color(0xFFFCF3E0)
private val md_surfaceContainer = Color(0xFFF6EDDA)
private val md_surfaceContainerHigh = Color(0xFFF0E7D5)
private val md_surfaceContainerHighest = Color(0xFFEAE2CF)

// Outline
private val md_outline = Color(0xFF7F775F)
private val md_outlineVariant = Color(0xFFD0C6AB)

// ─── Light color scheme ──────────────────────────────────────────────────────

val skillArcadeLightColors = lightColorScheme(
    primary = md_primary,
    onPrimary = md_onPrimary,
    primaryContainer = md_primaryContainer,
    onPrimaryContainer = md_onPrimaryContainer,
    inversePrimary = md_inversePrimary,
    secondary = md_secondary,
    onSecondary = md_onSecondary,
    secondaryContainer = md_secondaryContainer,
    onSecondaryContainer = md_onSecondaryContainer,
    tertiary = md_tertiary,
    onTertiary = md_onTertiary,
    tertiaryContainer = md_tertiaryContainer,
    onTertiaryContainer = md_onTertiaryContainer,
    error = md_error,
    onError = md_onError,
    errorContainer = md_errorContainer,
    onErrorContainer = md_onErrorContainer,
    background = md_background,
    onBackground = md_onBackground,
    surface = md_surface,
    onSurface = md_onSurface,
    surfaceVariant = md_surfaceVariant,
    onSurfaceVariant = md_onSurfaceVariant,
    surfaceTint = md_surfaceTint,
    inverseSurface = md_inverseSurface,
    inverseOnSurface = md_inverseOnSurface,
    outline = md_outline,
    outlineVariant = md_outlineVariant,
    surfaceDim = md_surfaceDim,
    surfaceBright = md_surfaceBright,
    surfaceContainerLowest = md_surfaceContainerLowest,
    surfaceContainerLow = md_surfaceContainerLow,
    surfaceContainer = md_surfaceContainer,
    surfaceContainerHigh = md_surfaceContainerHigh,
    surfaceContainerHighest = md_surfaceContainerHighest,
)

// ─── Dark color scheme (inverted / dark-mode equivalents) ───────────────────

val skillArcadeDarkColors = darkColorScheme(
    primary = md_inversePrimary,
    onPrimary = md_onPrimaryFixed,
    primaryContainer = md_onPrimaryFixedVariant,
    onPrimaryContainer = md_primaryFixed,
    inversePrimary = md_primary,
    secondary = md_secondaryFixedDim,
    onSecondary = md_onSecondaryFixed,
    secondaryContainer = md_onSecondaryFixedVariant,
    onSecondaryContainer = md_secondaryFixed,
    tertiary = md_tertiaryFixedDim,
    onTertiary = md_onTertiaryFixed,
    tertiaryContainer = md_onTertiaryFixedVariant,
    onTertiaryContainer = md_tertiaryFixed,
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF161208),
    onBackground = md_surfaceContainerHighest,
    surface = Color(0xFF161208),
    onSurface = md_surfaceContainerHighest,
    surfaceVariant = md_onSurfaceVariant,
    onSurfaceVariant = md_outlineVariant,
    surfaceTint = md_inversePrimary,
    inverseSurface = md_surfaceContainerHighest,
    inverseOnSurface = md_inverseSurface,
    outline = md_outline,
    outlineVariant = md_onSurfaceVariant,
    surfaceDim = Color(0xFF161208),
    surfaceBright = Color(0xFF3C382C),
    surfaceContainerLowest = Color(0xFF111005),
    surfaceContainerLow = Color(0xFF1F1B10),
    surfaceContainer = Color(0xFF231F13),
    surfaceContainerHigh = Color(0xFF2E2A1E),
    surfaceContainerHighest = Color(0xFF393529),
)
