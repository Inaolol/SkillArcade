package com.example.skillarcade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.skillarcade.ui.theme.SkillArcadeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkillArcadeTheme {
                // SkillArcadeNavHost will be wired here in Task H
                // Placeholder to keep the app launchable now:
                androidx.compose.material3.Text(
                    text = "SkillArcade Loading...",
                    modifier = androidx.compose.ui.Modifier
                )
            }
        }
    }
}