package com.example.skillarcade.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skillarcade.ui.components.ArcadeButton
import com.example.skillarcade.ui.theme.ClashDisplay

@Composable
fun CourseCatalogScreen(onOpenCourse: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "COURSE CATALOG",
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = ClashDisplay
            )
            Text(
                text = "TODO",
                style = MaterialTheme.typography.bodyMedium
            )
            ArcadeButton("Open Course", onClick = { onOpenCourse("course_1") })
        }
    }
}
