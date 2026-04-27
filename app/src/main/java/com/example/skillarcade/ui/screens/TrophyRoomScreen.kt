package com.example.skillarcade.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skillarcade.ui.components.ArcadeButton
import com.example.skillarcade.ui.theme.ClashDisplay

@Composable
fun TrophyRoomScreen(onNavigateToHome: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "TROPHY ROOM",
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = ClashDisplay
        )
        Spacer(modifier = Modifier.height(16.dp))
        ArcadeButton(text = "Go Home", onClick = onNavigateToHome)
    }
}
