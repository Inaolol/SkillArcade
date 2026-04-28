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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.skillarcade.domain.model.Rarity
import com.example.skillarcade.domain.model.Trophy
import com.example.skillarcade.domain.model.UserProgress
import com.example.skillarcade.ui.components.ArcadeButton
import com.example.skillarcade.ui.components.ArcadeChip
import com.example.skillarcade.ui.theme.ArcadeColors
import com.example.skillarcade.ui.theme.arcadeBorderShadow
import com.example.skillarcade.ui.viewmodel.TrophyRoomViewModel

@Composable
fun TrophyRoomScreen(onNavigateToHome: () -> Unit) {
    val vm: TrophyRoomViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val userProgress = uiState.userProgress
    val unlockedCount = uiState.trophies.count { it.isUnlocked }
    val totalCount = uiState.trophies.size

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            ProfileHeader(userProgress = userProgress)
        }
        item {
            StatsGrid(userProgress = userProgress)
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "YOUR TROPHIES",
                    style = MaterialTheme.typography.headlineMedium,
                    color = ArcadeColors.InkBlack
                )
                Text(
                    text = "$unlockedCount of $totalCount unlocked",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        item {
            TrophyRail(trophies = uiState.trophies)
        }
        items(uiState.trophies, key = { it.id }) { trophy ->
            TrophyDetailCard(trophy = trophy)
        }
        item {
            ArcadeButton(
                text = "← HOME",
                onClick = onNavigateToHome,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ProfileHeader(userProgress: UserProgress?) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .arcadeBorderShadow(
                    cornerRadius = 48.dp,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🎮", fontSize = 40.sp)
        }
        Text(
            text = "PLAYER",
            style = MaterialTheme.typography.headlineLarge,
            color = ArcadeColors.InkBlack
        )
        Text(
            text = "LEVEL ${userProgress?.level ?: 1}",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        ArcadeButton(
            text = "⚡ ${userProgress?.totalXp ?: 0} XP",
            onClick = {},
            modifier = Modifier.width(160.dp)
        )
    }
}

@Composable
private fun StatsGrid(userProgress: UserProgress?) {
    val rankLabel = when (userProgress?.level ?: 1) {
        in 1..3 -> "BRONZE"
        in 4..6 -> "SILVER"
        in 7..9 -> "GOLD"
        else -> "LEGEND"
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                value = "${userProgress?.totalXp ?: 0}",
                label = "TOTAL XP",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                value = "${userProgress?.lessonsCompleted ?: 0}",
                label = "LESSONS",
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                value = "${userProgress?.streak ?: 0}",
                label = "DAY STREAK",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                value = rankLabel,
                label = "RANK",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .arcadeBorderShadow(backgroundColor = MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = ArcadeColors.InkBlack,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = ArcadeColors.InkBlack,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun TrophyRail(trophies: List<Trophy>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(trophies, key = { it.id }) { trophy ->
            Column(
                modifier = Modifier.width(72.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .alpha(if (trophy.isUnlocked) 1f else 0.4f)
                        .arcadeBorderShadow(
                            cornerRadius = 36.dp,
                            shadowOffset = 2.dp,
                            backgroundColor = trophy.bgColor()
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = trophyIcon(trophy.iconKey),
                        fontSize = 32.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Text(
                    text = trophy.title,
                    style = MaterialTheme.typography.labelSmall,
                    color = ArcadeColors.InkBlack,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(72.dp)
                )
            }
        }
    }
}

@Composable
private fun TrophyDetailCard(trophy: Trophy) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (trophy.isUnlocked) 1f else 0.5f)
            .arcadeBorderShadow(
                backgroundColor = if (trophy.isUnlocked) {
                    trophy.rarity.rarityColor()
                } else {
                    MaterialTheme.colorScheme.surfaceContainerLowest
                }
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = trophyIcon(trophy.iconKey),
                fontSize = 32.sp,
                modifier = Modifier.widthIn(min = 40.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = trophy.title,
                    style = MaterialTheme.typography.labelLarge,
                    color = ArcadeColors.InkBlack
                )
                Text(
                    text = trophy.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            ArcadeChip(
                text = trophy.rarity.name,
                color = trophy.rarity.rarityColor()
            )
        }
    }
}

@Composable
private fun Trophy.bgColor(): Color =
    if (!isUnlocked) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        rarity.rarityColor()
    }

@Composable
private fun Rarity.rarityColor(): Color = when (this) {
    Rarity.COMMON -> MaterialTheme.colorScheme.surfaceContainerHigh
    Rarity.RARE -> MaterialTheme.colorScheme.tertiaryContainer
    Rarity.EPIC -> MaterialTheme.colorScheme.secondaryContainer
    Rarity.LEGENDARY -> MaterialTheme.colorScheme.primaryContainer
}

private fun trophyIcon(iconKey: String): String = when (iconKey.lowercase()) {
    "star" -> "⭐"
    "bolt", "lightning" -> "⚡"
    "trophy", "emoji_events" -> "🏆"
    "fire", "local_fire_department" -> "🔥"
    "book", "school" -> "📚"
    "rocket" -> "🚀"
    "crown" -> "👑"
    "medal" -> "🎖️"
    else -> "🏅"
}
