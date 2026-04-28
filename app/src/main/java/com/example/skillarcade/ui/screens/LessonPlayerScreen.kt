package com.example.skillarcade.ui.screens

import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.skillarcade.domain.model.Lesson
import com.example.skillarcade.ui.components.ArcadeButton
import com.example.skillarcade.ui.components.ArcadeChip
import com.example.skillarcade.ui.theme.ArcadeColors
import com.example.skillarcade.ui.theme.ArcadeTokens
import com.example.skillarcade.ui.theme.arcadeBorderShadow
import com.example.skillarcade.ui.viewmodel.LessonPlayerViewModel
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun LessonPlayerScreen(lessonId: String, onBack: () -> Unit) {
    val vm: LessonPlayerViewModel = hiltViewModel(key = lessonId)
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        BackButtonRow(onBack = onBack)

        uiState.lesson?.let { lesson ->
            YouTubePlayerCard(lesson = lesson)
            LessonInfoSection(lesson = lesson)
            CompleteSection(
                isCompleted = uiState.isCompleted,
                onComplete = vm::completeLesson,
                onBack = onBack
            )
        }
    }
}

@Composable
private fun BackButtonRow(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text(
                text = "← BACK",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun extractVideoId(youtubeUrl: String): String =
    youtubeUrl.substringAfter("v=").substringBefore("&")

@Composable
private fun YouTubePlayerCard(lesson: Lesson) {
    val videoId = extractVideoId(lesson.youtubeUrl)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .aspectRatio(16f / 9f)
            .arcadeBorderShadow(
                cornerRadius = ArcadeTokens.CornerRadius,
                backgroundColor = ArcadeColors.InkBlack
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(ArcadeTokens.CornerRadius))
                .background(ArcadeColors.InkBlack)
        ) {
            if (videoId.isNotBlank()) {
                AndroidView(
                    factory = { ctx ->
                        WebView(ctx).apply {
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            settings.mediaPlaybackRequiresUserGesture = false
                            webChromeClient = WebChromeClient()
                            loadUrl("https://www.youtube.com/embed/$videoId")
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    text = "▶",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        ArcadeChip(
            text = "${lesson.durationMinutes} min",
            color = ArcadeColors.PrimaryYellow,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        )
    }
}

@Composable
private fun LessonInfoSection(lesson: Lesson) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ArcadeChip(
                text = "⚡ ${lesson.xpReward} XP",
                color = ArcadeColors.PrimaryYellow
            )
        }
        Text(
            text = lesson.title,
            style = MaterialTheme.typography.headlineLarge,
            color = ArcadeColors.InkBlack,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${lesson.durationMinutes} min · Lesson ${lesson.orderIndex + 1}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CompleteSection(
    isCompleted: Boolean,
    onComplete: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isCompleted) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .arcadeBorderShadow(
                        backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓ LESSON COMPLETE!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = ArcadeColors.InkBlack,
                    textAlign = TextAlign.Center
                )
            }
            ArcadeButton(
                text = "← BACK TO COURSE",
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            ArcadeButton(
                text = "MARK AS COMPLETE ✓",
                onClick = onComplete,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
