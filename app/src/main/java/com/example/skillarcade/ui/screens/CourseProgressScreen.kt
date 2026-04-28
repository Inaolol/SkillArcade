package com.example.skillarcade.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.skillarcade.domain.model.Course
import com.example.skillarcade.domain.model.Difficulty
import com.example.skillarcade.domain.model.Lesson
import com.example.skillarcade.ui.components.ArcadeChip
import com.example.skillarcade.ui.components.ArcadeProgressBar
import com.example.skillarcade.ui.theme.ArcadeColors
import com.example.skillarcade.ui.theme.arcadeBorderShadow
import com.example.skillarcade.ui.viewmodel.CourseProgressViewModel

@Composable
fun CourseProgressScreen(
    courseId: String,
    onOpenLesson: (lessonId: String) -> Unit,
    onBack: () -> Unit
) {
    val vm: CourseProgressViewModel = hiltViewModel(key = courseId)
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BackButtonRow(onBack = onBack)
        }

        if (uiState.isLoading) {
            item {
                LoadingState()
            }
        } else {
            uiState.course?.let { course ->
                item {
                    CourseHeader(course = course)
                }
            }

            item {
                Text(
                    text = "LESSONS",
                    style = MaterialTheme.typography.headlineMedium,
                    color = ArcadeColors.InkBlack
                )
            }

            itemsIndexed(uiState.lessons, key = { _, lesson -> lesson.id }) { index, lesson ->
                LessonProgressItem(
                    lesson = lesson,
                    lessonNumber = index + 1,
                    onOpenLesson = { onOpenLesson(lesson.id) }
                )
            }
        }
    }
}

@Composable
private fun BackButtonRow(onBack: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
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
private fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 96.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Loading...",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CourseHeader(course: Course) {
    val progress = course.progressFraction()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .arcadeBorderShadow(backgroundColor = MaterialTheme.colorScheme.primaryContainer)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ArcadeChip(
                text = course.difficulty.name,
                color = course.difficulty.difficultyColor()
            )
            XpRewardBadge(xpReward = course.xpReward)
        }

        Text(
            text = course.title.uppercase(),
            style = MaterialTheme.typography.headlineLarge,
            color = ArcadeColors.InkBlack,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ArcadeProgressBar(
                progress = progress,
                color = ArcadeColors.InkBlack,
                trackColor = MaterialTheme.colorScheme.surfaceContainerLowest
            )
            Text(
                text = "${course.completedLessons}/${course.totalLessons} lessons completed",
                style = MaterialTheme.typography.labelLarge,
                color = ArcadeColors.InkBlack
            )
        }
    }
}

@Composable
private fun XpRewardBadge(xpReward: Int) {
    Box(
        modifier = Modifier
            .arcadeBorderShadow(
                cornerRadius = 50.dp,
                shadowOffset = 2.dp,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "⚡ $xpReward XP",
            style = MaterialTheme.typography.labelLarge,
            color = ArcadeColors.InkBlack
        )
    }
}

@Composable
private fun LessonProgressItem(
    lesson: Lesson,
    lessonNumber: Int,
    onOpenLesson: () -> Unit
) {
    val backgroundColor = if (lesson.isCompleted) {
        MaterialTheme.colorScheme.tertiaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceContainerLowest
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .arcadeBorderShadow(backgroundColor = backgroundColor)
            .clickable(onClick = onOpenLesson)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LessonNumberCircle(
                lessonNumber = lessonNumber,
                isCompleted = lesson.isCompleted
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = lesson.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = ArcadeColors.InkBlack,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${lesson.durationMinutes} min",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = if (lesson.isCompleted) "✓" else "→",
                style = MaterialTheme.typography.headlineMedium,
                color = ArcadeColors.InkBlack,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun LessonNumberCircle(
    lessonNumber: Int,
    isCompleted: Boolean
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .background(
                color = if (isCompleted) ArcadeColors.InkBlack
                        else MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = lessonNumber.toString(),
            style = MaterialTheme.typography.labelLarge,
            color = if (isCompleted) Color.White else ArcadeColors.InkBlack
        )
    }
}

private fun Course.progressFraction(): Float =
    if (totalLessons > 0) completedLessons.toFloat() / totalLessons else 0f

@Composable
private fun Difficulty.difficultyColor(): Color = when (this) {
    Difficulty.EASY -> MaterialTheme.colorScheme.tertiaryContainer
    Difficulty.MEDIUM -> MaterialTheme.colorScheme.primaryContainer
    Difficulty.HARD -> MaterialTheme.colorScheme.secondaryContainer
}
