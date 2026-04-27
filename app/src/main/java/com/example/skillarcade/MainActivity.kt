package com.example.skillarcade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.skillarcade.ui.navigation.SkillArcadeNavHost
import com.example.skillarcade.ui.theme.SkillArcadeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkillArcadeTheme {
                SkillArcadeNavHost()
            }
        }
    }
}