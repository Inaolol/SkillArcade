package com.example.skillarcade.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect

@Composable
fun SkillArcadeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) skillArcadeDarkColors else skillArcadeLightColors

    SideEffect {
        // Status bar color handling can be added here if needed
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SkillArcadeTypography,
        shapes = SkillArcadeShapes,
        content = content
    )
}
