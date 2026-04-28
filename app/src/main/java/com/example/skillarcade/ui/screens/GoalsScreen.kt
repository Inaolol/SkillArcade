package com.example.skillarcade.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.skillarcade.domain.model.Goal
import com.example.skillarcade.domain.model.GoalType
import com.example.skillarcade.ui.components.ArcadeButton
import com.example.skillarcade.ui.components.ArcadeChip
import com.example.skillarcade.ui.components.ArcadeProgressBar
import com.example.skillarcade.ui.theme.ArcadeColors
import com.example.skillarcade.ui.theme.arcadeBorderShadow
import com.example.skillarcade.ui.viewmodel.GoalsViewModel

@Composable
fun GoalsScreen(onNavigateToCourses: () -> Unit) {
    val vm: GoalsViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val completedCount = uiState.goals.count { it.isCompleted }
    val totalCount = uiState.goals.size
    val totalXpEarned = uiState.goals
        .filter { it.isCompleted }
        .sumOf { it.xpReward }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            StreakHero(streak = uiState.userProgress?.streak ?: 0)
        }
        item {
            ProgressSummaryChips(
                completedCount = completedCount,
                totalCount = totalCount,
                totalXpEarned = totalXpEarned
            )
        }
        item {
            Text(
                text = "DAILY QUESTS",
                style = MaterialTheme.typography.headlineLarge,
                color = ArcadeColors.InkBlack
            )
        }

        if (uiState.goals.isEmpty()) {
            item {
                EmptyGoalsCard()
            }
        } else {
            items(uiState.goals, key = { it.id }) { goal ->
                GoalCard(goal = goal)
            }
            item {
                ArcadeButton(
                    text = "BROWSE COURSES →",
                    onClick = onNavigateToCourses,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun StreakHero(streak: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "🔥",
            fontSize = 72.sp
        )
        Text(
            text = "$streak DAY STREAK",
            style = MaterialTheme.typography.displayLarge,
            color = ArcadeColors.InkBlack,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Keep the momentum going!",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ProgressSummaryChips(
    completedCount: Int,
    totalCount: Int,
    totalXpEarned: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        ArcadeChip(
            text = "$completedCount/$totalCount DONE",
            color = MaterialTheme.colorScheme.tertiaryContainer
        )
        ArcadeChip(
            text = "⚡ $totalXpEarned XP",
            color = MaterialTheme.colorScheme.primaryContainer
        )
    }
}

@Composable
private fun GoalCard(goal: Goal) {
    val backgroundColor = if (goal.isCompleted) {
        MaterialTheme.colorScheme.tertiaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceContainerLowest
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (goal.isCompleted) 0.8f else 1f)
            .arcadeBorderShadow(backgroundColor = backgroundColor)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            GoalIconBox(goal = goal)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = goal.title,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (goal.isCompleted) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        ArcadeColors.InkBlack
                    },
                    textDecoration = if (goal.isCompleted) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    }
                )
                ArcadeProgressBar(progress = goal.progressFraction())
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${goal.currentValue}/${goal.targetValue}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ArcadeChip(
                        text = "⚡ ${goal.xpReward} XP",
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                }
            }
        }
    }
}

@Composable
private fun GoalIconBox(goal: Goal) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .arcadeBorderShadow(
                cornerRadius = 8.dp,
                shadowOffset = 2.dp,
                backgroundColor = if (goal.isCompleted) {
                    MaterialTheme.colorScheme.tertiaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceContainerHigh
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (goal.isCompleted) "✓" else goal.goalType.emoji(),
            style = MaterialTheme.typography.headlineMedium,
            color = ArcadeColors.InkBlack,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EmptyGoalsCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .arcadeBorderShadow(backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "🎯", fontSize = 48.sp)
            Text(
                text = "No quests today — check back soon!",
                style = MaterialTheme.typography.headlineMedium,
                color = ArcadeColors.InkBlack,
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun Goal.progressFraction(): Float =
    if (targetValue == 0) 0f else (currentValue.toFloat() / targetValue).coerceIn(0f, 1f)

private fun GoalType.emoji(): String = when (this) {
    GoalType.LESSONS_COMPLETED -> "📖"
    GoalType.COURSES_COMPLETED -> "🎓"
    GoalType.XP_EARNED -> "⚡"
    GoalType.STREAK_DAYS -> "🔥"
}
