package com.example.skillarcade.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillarcade.ui.theme.ArcadeColors
import com.example.skillarcade.ui.theme.ArcadeTokens
import com.example.skillarcade.ui.theme.Epilogue
import com.example.skillarcade.ui.theme.arcadeBorderShadow

enum class NavTab(val label: String, val route: String, val emoji: String) {
    Home("HOME", "home", "🏠"),
    Learn("LEARN", "course_catalog", "🎓"),
    Goals("GOALS", "goals", "🏆"),
    Me("ME", "trophy_room", "👤")
}

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .drawBehind {
                val strokeWidth = ArcadeTokens.BorderWidth.toPx()
                drawLine(
                    color = ArcadeColors.InkBlack,
                    start = Offset(0f, strokeWidth / 2f),
                    end = Offset(size.width, strokeWidth / 2f),
                    strokeWidth = strokeWidth
                )
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavTab.entries.forEach { tab ->
                NavTabItem(
                    tab = tab,
                    isSelected = currentRoute == tab.route,
                    onNavigate = onNavigate
                )
            }
        }
        Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
    }
}

@Composable
private fun RowScope.NavTabItem(
    tab: NavTab,
    isSelected: Boolean,
    onNavigate: (String) -> Unit
) {
    val contentColor = if (isSelected) ArcadeColors.InkBlack else MaterialTheme.colorScheme.onSurfaceVariant
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

    Box(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 4.dp)
            .then(
                if (isSelected) {
                    Modifier.arcadeBorderShadow(
                        cornerRadius = 12.dp,
                        shadowOffset = 2.dp,
                        backgroundColor = ArcadeColors.PrimaryYellow
                    )
                } else Modifier
            )
            .clickable(
                onClickLabel = tab.label,
                role = Role.Tab,
                onClick = { onNavigate(tab.route) }
            )
            .semantics { selected = isSelected }
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = tab.emoji, fontSize = 20.sp)
            Text(
                text = tab.label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = Epilogue,
                    fontWeight = fontWeight,
                    color = contentColor,
                    letterSpacing = 0.5.sp
                )
            )
        }
    }
}
