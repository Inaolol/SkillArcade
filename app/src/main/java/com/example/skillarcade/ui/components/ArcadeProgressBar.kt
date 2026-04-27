package com.example.skillarcade.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skillarcade.ui.theme.ArcadeColors
import com.example.skillarcade.ui.theme.ArcadeTokens
import com.example.skillarcade.ui.theme.SkillArcadeTheme
import com.example.skillarcade.ui.theme.arcadeBorderShadow

@Composable
fun ArcadeProgressBar(
    progress: Float,   // 0f..1f
    modifier: Modifier = Modifier,
    color: Color = ArcadeColors.PrimaryYellow,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    val clampedProgress = progress.coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .height(20.dp)
            .fillMaxWidth()
            .arcadeBorderShadow(
                cornerRadius = ArcadeTokens.CornerRadius,
                shadowOffset = 2.dp,
                backgroundColor = trackColor
            )
            .clip(RoundedCornerShape(ArcadeTokens.CornerRadius))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(clampedProgress)
                .background(color)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArcadeProgressBarPreview() {
    SkillArcadeTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ArcadeProgressBar(progress = 0.25f)
            ArcadeProgressBar(progress = 0.5f, color = ArcadeColors.AccentMint)
            ArcadeProgressBar(progress = 0.75f, color = ArcadeColors.AccentPink)
            ArcadeProgressBar(progress = 1.0f)
        }
    }
}
