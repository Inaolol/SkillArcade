package com.example.skillarcade.ui.theme

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ─── Design tokens ───────────────────────────────────────────────────────────

object ArcadeTokens {
    val BorderWidth = 3.dp
    val ShadowOffset = 4.dp
    val PressShadowOffset = 2.dp
    val BorderColor = ArcadeColors.InkBlack
    val CornerRadius = 12.dp
    val LargeCornerRadius = 20.dp
}

// ─── Hard-shadow modifier ────────────────────────────────────────────────────

/**
 * Applies the Neo-Boutique "hard drop shadow" look:
 *  - A solid, offset rectangle drawn behind the composable (no blur)
 *  - A 3px ink-black border around the composable
 *
 * The shadow is painted first (behind), then an optional background fill,
 * then the composable sits on top — giving the 2.5D physical presence
 * described in the design system.
 *
 * For the press state, call with shadowOffset = ArcadeTokens.PressShadowOffset
 * and pair with an offset(x = 2.dp, y = 2.dp) on the composable.
 */
fun Modifier.arcadeBorderShadow(
    cornerRadius: Dp = ArcadeTokens.CornerRadius,
    shadowColor: Color = ArcadeColors.InkBlack,
    shadowOffset: Dp = ArcadeTokens.ShadowOffset,
    borderWidth: Dp = ArcadeTokens.BorderWidth,
    borderColor: Color = ArcadeColors.InkBlack,
    backgroundColor: Color = Color.Transparent
): Modifier = this
    .offset(x = 0.dp, y = 0.dp)
    .drawBehind {
        val shadowPx = shadowOffset.toPx()
        val cornerPx = cornerRadius.toPx()

        // Hard shadow: filled rounded rect offset down-right
        drawRoundRect(
            color = shadowColor,
            topLeft = Offset(shadowPx, shadowPx),
            size = size,
            cornerRadius = CornerRadius(cornerPx)
        )

        // Optional background fill (drawn on top of shadow, below the border)
        if (backgroundColor != Color.Transparent) {
            drawRoundRect(
                color = backgroundColor,
                size = size,
                cornerRadius = CornerRadius(cornerPx)
            )
        }
    }
    .border(
        width = borderWidth,
        color = borderColor,
        shape = RoundedCornerShape(cornerRadius)
    )
