package com.example.skillarcade.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skillarcade.ui.theme.SkillArcadeTheme
import com.example.skillarcade.ui.theme.arcadeBorderShadow

@Composable
fun ArcadeCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .arcadeBorderShadow(backgroundColor = backgroundColor)
            .padding(16.dp),
        content = content
    )
}

@Preview(showBackground = true)
@Composable
private fun ArcadeCardPreview() {
    SkillArcadeTheme {
        ArcadeCard(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Card Title",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "This is card body content that sits inside the ArcadeCard container.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
