package com.example.skillarcade.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.skillarcade.domain.model.Course
import com.example.skillarcade.domain.model.Difficulty
import com.example.skillarcade.domain.model.UserProgress
import com.example.skillarcade.ui.components.ArcadeButton
import com.example.skillarcade.ui.components.ArcadeChip
import com.example.skillarcade.ui.components.ArcadeProgressBar
import com.example.skillarcade.ui.theme.ArcadeColors
import com.example.skillarcade.ui.theme.arcadeBorderShadow
import com.example.skillarcade.ui.viewmodel.HomeDashboardViewModel

@Composable
fun HomeDashboardScreen(onOpenCourse: (String) -> Unit) {
    val vm: HomeDashboardViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    var selectedCategory by remember { mutableStateOf("ALL") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            DashboardHeader(streak = uiState.userProgress?.streak ?: 0)
        }
        item {
            WelcomeSection()
        }
        if (uiState.inProgressCourses.isEmpty()) {
            item {
                CategoryChipsRow(selected = selectedCategory, onSelect = { selectedCategory = it })
            }
            item {
                SectionHeading("CONTINUE LEARNING")
            }
            item {
                EmptyCoursesCard()
            }
        } else {
            item {
                HeroCard(
                    course = uiState.inProgressCourses.first(),
                    onResume = { onOpenCourse(uiState.inProgressCourses.first().id) }
                )
            }
            item {
                CategoryChipsRow(selected = selectedCategory, onSelect = { selectedCategory = it })
            }
            val remaining = uiState.inProgressCourses.drop(1)
            if (remaining.isNotEmpty()) {
                item {
                    SectionHeading("CONTINUE LEARNING")
                }
                items(remaining, key = { it.id }) { course ->
                    CourseProgressCard(course = course, onClick = { onOpenCourse(course.id) })
                }
            }
        }
    }
}

@Composable
private fun DashboardHeader(streak: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "SKILLARCADE",
            style = MaterialTheme.typography.headlineMedium,
            color = ArcadeColors.InkBlack
        )
        Box(
            modifier = Modifier
                .arcadeBorderShadow(
                    cornerRadius = 50.dp,
                    shadowOffset = 2.dp,
                    backgroundColor = ArcadeColors.PrimaryYellow
                )
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "🔥 $streak",
                style = MaterialTheme.typography.labelLarge,
                color = ArcadeColors.InkBlack
            )
        }
    }
}

@Composable
private fun WelcomeSection() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = "HI THERE!",
            style = MaterialTheme.typography.headlineLarge,
            color = ArcadeColors.InkBlack
        )
        Text(
            text = "Ready to level up today?",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun HeroCard(course: Course, onResume: () -> Unit) {
    val progress = if (course.totalLessons > 0) {
        course.completedLessons.toFloat() / course.totalLessons
    } else 0f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .arcadeBorderShadow(backgroundColor = MaterialTheme.colorScheme.tertiaryContainer)
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .background(ArcadeColors.InkBlack, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "JUMP BACK IN",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
            }
            Text(
                text = course.title,
                style = MaterialTheme.typography.headlineMedium,
                color = ArcadeColors.InkBlack,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ArcadeProgressBar(
                    progress = progress,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelLarge,
                    color = ArcadeColors.InkBlack
                )
            }
            ArcadeButton(
                text = "RESUME →",
                onClick = onResume,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun CategoryChipsRow(selected: String, onSelect: (String) -> Unit) {
    val categories = listOf("ALL", "POPULAR", "NEWEST")
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories) { category ->
            ArcadeChip(
                text = category,
                color = if (category == selected) ArcadeColors.PrimaryYellow
                        else MaterialTheme.colorScheme.surfaceContainerHigh,
                modifier = Modifier.clickable { onSelect(category) }
            )
        }
    }
}

@Composable
private fun SectionHeading(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        color = ArcadeColors.InkBlack
    )
}

@Composable
private fun CourseProgressCard(course: Course, onClick: () -> Unit) {
    val progress = if (course.totalLessons > 0) {
        course.completedLessons.toFloat() / course.totalLessons
    } else 0f

    val thumbnailColor = when (course.difficulty) {
        Difficulty.EASY -> MaterialTheme.colorScheme.tertiaryContainer
        Difficulty.MEDIUM -> MaterialTheme.colorScheme.primaryContainer
        Difficulty.HARD -> MaterialTheme.colorScheme.secondaryContainer
    }
    val difficultyChipColor = thumbnailColor

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .arcadeBorderShadow(backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .arcadeBorderShadow(cornerRadius = 8.dp, backgroundColor = thumbnailColor)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceContainerLowest,
                            RoundedCornerShape(4.dp)
                        )
                        .border(2.dp, ArcadeColors.InkBlack, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.labelLarge,
                        color = ArcadeColors.InkBlack
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = ArcadeColors.InkBlack,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )
                ArcadeChip(text = course.difficulty.name, color = difficultyChipColor)
            }
            ArcadeProgressBar(progress = progress)
        }
    }
}

@Composable
private fun EmptyCoursesCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .arcadeBorderShadow(backgroundColor = MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "🎮",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = "NO COURSES STARTED YET",
                style = MaterialTheme.typography.headlineMedium,
                color = ArcadeColors.InkBlack,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Browse the Courses tab to begin your arcade journey!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
