package com.example.skillarcade.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skillarcade.ui.theme.ArcadeColors
import com.example.skillarcade.ui.theme.ArcadeTokens
import com.example.skillarcade.ui.theme.Epilogue
import com.example.skillarcade.ui.theme.SkillArcadeTheme

enum class NavTab(val label: String, val route: String) {
    Home("Home", "home"),
    Courses("Courses", "course_catalog"),
    Goals("Goals", "goals"),
    Trophies("Trophies", "trophy_room")
}

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .drawBehind {
                drawLine(
                    color = ArcadeColors.InkBlack,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = ArcadeTokens.BorderWidth.toPx()
                )
            }
            .navigationBarsPadding()
            .height(64.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavTab.entries.forEach { tab ->
            val isSelected = currentRoute == tab.route
            NavTabItem(
                tab = tab,
                isSelected = isSelected,
                onNavigate = onNavigate
            )
        }
    }
}

@Composable
private fun RowScope.NavTabItem(
    tab: NavTab,
    isSelected: Boolean,
    onNavigate: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable { onNavigate(tab.route) }
            .background(
                if (isSelected) ArcadeColors.PrimaryYellow.copy(alpha = 0.15f)
                else Color.Transparent
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = tab.label.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = Epilogue,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) ArcadeColors.InkBlack else MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavBarPreview() {
    SkillArcadeTheme {
        BottomNavBar(
            currentRoute = "home",
            onNavigate = {}
        )
    }
}
