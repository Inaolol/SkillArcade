package com.example.skillarcade.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SkillArcadeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) skillArcadeDarkColors else skillArcadeLightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SkillArcadeTypography,
        shapes = SkillArcadeShapes,
        content = content
    )
}
