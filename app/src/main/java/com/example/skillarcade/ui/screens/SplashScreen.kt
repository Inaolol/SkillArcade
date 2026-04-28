package com.example.skillarcade.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillarcade.ui.theme.ArcadeColors
import com.example.skillarcade.ui.theme.arcadeBorderShadow
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToOnboarding: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2_000)
        onNavigateToOnboarding()
    }

    val logoShadowOffset = with(LocalDensity.current) { 4.dp.toPx() }
    val backgroundGradient = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.primaryContainer
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .padding(horizontal = 12.dp, vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = "SKILLARCADE",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 40.sp,
                        shadow = Shadow(
                            color = ArcadeColors.InkBlack,
                            offset = Offset(logoShadowOffset, logoShadowOffset),
                            blurRadius = 0f
                        )
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .arcadeBorderShadow(
                            cornerRadius = 12.dp,
                            backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🔥",
                        fontSize = 40.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .arcadeBorderShadow(
                            cornerRadius = 50.dp,
                            backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest
                        )
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerLowest,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "LOADING ARCADE...",
                        style = MaterialTheme.typography.labelLarge.copy(letterSpacing = 4.sp),
                        color = ArcadeColors.InkBlack
                    )
                }
            }
        }
    }
}
