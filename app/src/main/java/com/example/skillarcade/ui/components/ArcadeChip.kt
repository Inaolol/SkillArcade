package com.example.skillarcade.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skillarcade.ui.theme.ArcadeColors
import com.example.skillarcade.ui.theme.ArcadeTokens
import com.example.skillarcade.ui.theme.Epilogue
import com.example.skillarcade.ui.theme.SkillArcadeTheme

// Note: ArcadeChip intentionally does NOT use arcadeBorderShadow — it uses border() directly.
// Chips are secondary elements; no shadow per design spec.
@Composable
fun ArcadeChip(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = ArcadeColors.AccentMint,
    textColor: Color = ArcadeColors.InkBlack
) {
    Box(
        modifier = modifier
            .background(color = color, shape = RoundedCornerShape(ArcadeTokens.CornerRadius))
            .border(
                width = ArcadeTokens.BorderWidth,
                color = ArcadeColors.InkBlack,
                shape = RoundedCornerShape(ArcadeTokens.CornerRadius)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = Epilogue,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArcadeChipPreview() {
    SkillArcadeTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ArcadeChip(text = "Beginner")
            ArcadeChip(text = "In Progress", color = ArcadeColors.PrimaryYellow)
            ArcadeChip(text = "Complete", color = ArcadeColors.AccentPink, textColor = ArcadeColors.InkBlack)
        }
    }
}
