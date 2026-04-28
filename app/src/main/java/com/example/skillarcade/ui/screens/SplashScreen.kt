package com.example.skillarcade.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.skillarcade.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToOnboarding: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3_000)
        onNavigateToOnboarding()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF81E481))
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "SkillArcade Splash Screen",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
