package com.example.skillarcade.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillarcade.ui.components.ArcadeButton
import com.example.skillarcade.ui.theme.ArcadeColors
import com.example.skillarcade.ui.theme.arcadeBorderShadow

private data class OnboardingSlide(
    val headline: String,
    val body: String,
    val illustration: String,
    val backgroundColor: Color
)

@Composable
fun OnboardingScreen(page: Int, onNext: () -> Unit) {
    val slides = listOf(
        OnboardingSlide(
            headline = "Level up your skills like it's an arcade game \uD83D\uDD25",
            body = "Master new abilities, complete daily quests, and dominate the leaderboard in style.",
            illustration = "\uD83D\uDD79",
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        OnboardingSlide(
            headline = "Earn XP, streaks, and trophies while you learn",
            body = "Track your progress with real-time stats and celebrate every milestone.",
            illustration = "\uD83C\uDFC6",
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        ),
        OnboardingSlide(
            headline = "Design, code, marketing \u2014 bite-sized \u0026 fun",
            body = "Short lessons that fit your schedule, with gamified challenges to keep you hooked.",
            illustration = "\uD83D\uDCBB",
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        OnboardingSlide(
            headline = "Ready to Play?",
            body = "Your ultimate skill-building journey starts right now. Step into the arena.",
            illustration = "\uD83C\uDFAE",
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    )
    val currentPage = page.coerceIn(slides.indices)
    val slide = slides[currentPage]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { repeat(4 - currentPage) { onNext() } }) {
                    Text(
                        text = "SKIP",
                        style = MaterialTheme.typography.labelLarge.copy(letterSpacing = 2.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            OnboardingIllustrationCard(slide = slide)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = if (currentPage == 3) slide.headline.uppercase() else slide.headline,
                    style = MaterialTheme.typography.headlineLarge,
                    color = ArcadeColors.InkBlack,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = slide.body,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                ProgressDots(activePage = currentPage)
                ArcadeButton(
                    text = if (currentPage < 3) "NEXT \u2192" else "GET STARTED \u2192",
                    onClick = onNext,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun OnboardingIllustrationCard(slide: OnboardingSlide) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(4f / 5f)
            .arcadeBorderShadow(
                cornerRadius = 12.dp,
                backgroundColor = slide.backgroundColor
            )
            .padding(18.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.TopStart)
                .arcadeBorderShadow(
                    cornerRadius = 24.dp,
                    shadowOffset = 2.dp,
                    backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "\u2605",
                style = MaterialTheme.typography.headlineMedium,
                color = ArcadeColors.InkBlack
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .rotate(-4f),
            text = slide.illustration,
            fontSize = 112.sp,
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .arcadeBorderShadow(
                    cornerRadius = 50.dp,
                    shadowOffset = 2.dp,
                    backgroundColor = MaterialTheme.colorScheme.secondaryContainer
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "LVL 1",
                style = MaterialTheme.typography.labelLarge.copy(letterSpacing = 1.sp),
                color = ArcadeColors.InkBlack
            )
        }
    }
}

@Composable
private fun ProgressDots(activePage: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(4) { index ->
            if (index == activePage) {
                Spacer(
                    modifier = Modifier
                        .width(32.dp)
                        .height(12.dp)
                        .arcadeBorderShadow(
                            cornerRadius = 6.dp,
                            shadowOffset = 2.dp,
                            backgroundColor = MaterialTheme.colorScheme.primaryContainer
                        )
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = CircleShape
                        )
                        .border(
                            width = 3.dp,
                            color = ArcadeColors.InkBlack,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}
