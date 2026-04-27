package com.example.skillarcade.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skillarcade.ui.theme.ArcadeColors
import com.example.skillarcade.ui.theme.ArcadeTokens
import com.example.skillarcade.ui.theme.ClashDisplay
import com.example.skillarcade.ui.theme.SkillArcadeTheme
import com.example.skillarcade.ui.theme.arcadeBorderShadow

enum class ArcadeButtonVariant { Primary, Success, Destructive }

@Composable
fun ArcadeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ArcadeButtonVariant = ArcadeButtonVariant.Primary,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor = when (variant) {
        ArcadeButtonVariant.Primary -> ArcadeColors.PrimaryYellow
        ArcadeButtonVariant.Success -> ArcadeColors.AccentMint
        ArcadeButtonVariant.Destructive -> ArcadeColors.AccentPink
    }

    val shadowOffset = if (isPressed) ArcadeTokens.PressShadowOffset else ArcadeTokens.ShadowOffset
    val pressOffset = if (isPressed) 2.dp else 0.dp
    val pressOffsetPx = with(LocalDensity.current) { pressOffset.toPx() }

    Box(
        modifier = modifier
            .graphicsLayer {
                translationX = pressOffsetPx
                translationY = pressOffsetPx
            }
            .arcadeBorderShadow(
                backgroundColor = if (enabled) backgroundColor else Color.Gray,
                shadowOffset = shadowOffset
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = 24.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelLarge.copy(
                fontFamily = ClashDisplay,
                fontWeight = FontWeight.Bold,
                color = if (enabled) ArcadeColors.InkBlack else Color.DarkGray
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArcadeButtonPreview() {
    SkillArcadeTheme {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ArcadeButton("Start Learning", onClick = {})
            ArcadeButton("Complete", onClick = {}, variant = ArcadeButtonVariant.Success)
            ArcadeButton("Delete", onClick = {}, variant = ArcadeButtonVariant.Destructive)
            ArcadeButton("Disabled", onClick = {}, enabled = false)
        }
    }
}
