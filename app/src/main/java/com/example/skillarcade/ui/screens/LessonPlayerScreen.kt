package com.example.skillarcade.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.skillarcade.ui.video.YOUTUBE_PLAYER_BASE_URL
import com.example.skillarcade.ui.video.buildYouTubePlayerHtml
import com.example.skillarcade.ui.video.extractYouTubeVideoId
import com.example.skillarcade.ui.viewmodel.LessonPlayerViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import kotlinx.coroutines.delay

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

@Composable
private fun YouTubePlayerCard(lesson: Lesson) {
    val context = LocalContext.current
    val videoId = remember(lesson.youtubeUrl) { extractYouTubeVideoId(lesson.youtubeUrl) }
    val playerHtml = remember(videoId) { videoId?.let(::buildYouTubePlayerHtml) }
    var isLoading by remember(playerHtml) { mutableStateOf(playerHtml != null) }
    var hasError by remember(playerHtml) { mutableStateOf(playerHtml == null) }

    LaunchedEffect(playerHtml) {
        if (playerHtml != null) {
            delay(10_000)
            if (isLoading && !hasError) {
                isLoading = false
                hasError = true
            }
        }
    }

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
            if (playerHtml != null) {
                AndroidView(
                    factory = { ctx ->
                        WebView(ctx).apply {
                            setBackgroundColor(android.graphics.Color.BLACK)
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            settings.mediaPlaybackRequiresUserGesture = false
                            settings.loadsImagesAutomatically = true
                            settings.userAgentString = "Mozilla/5.0 (Linux; Android 13; Pixel 7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Mobile Safari/537.36"
                            webChromeClient = WebChromeClient()
                        }
                    },
                    update = { webView ->
                        webView.webViewClient = object : WebViewClient() {
                            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                                isLoading = true
                                hasError = false
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                isLoading = false
                            }

                            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                                if (request?.isForMainFrame != false) {
                                    isLoading = false
                                    hasError = true
                                }
                            }

                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                val url = request?.url ?: return false
                                
                                if (request?.isForMainFrame == true) {
                                    val host = url.host.orEmpty()
                                    val canStay = host.endsWith("youtube.com") || 
                                                 host.endsWith("youtube-nocookie.com")
                                    
                                    if (!canStay) {
                                        openExternalUrl(context, url.toString())
                                        return true
                                    }
                                }
                                return false
                            }
                        }

                        if (webView.tag != playerHtml) {
                            webView.tag = playerHtml
                            webView.loadDataWithBaseURL(YOUTUBE_PLAYER_BASE_URL, playerHtml!!, "text/html", "UTF-8", null)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            if (isLoading) {
                Text(
                    text = "LOADING VIDEO...",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            if (hasError) {
                VideoFallback(
                    onOpenExternal = { openExternalUrl(context, lesson.youtubeUrl) },
                    modifier = Modifier.align(Alignment.Center)
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
private fun VideoFallback(
    onOpenExternal: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "VIDEO UNAVAILABLE",
            style = MaterialTheme.typography.labelLarge,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        ArcadeButton(
            text = "OPEN IN YOUTUBE",
            onClick = onOpenExternal
        )
    }
}

private fun openExternalUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    runCatching { context.startActivity(intent) }
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
