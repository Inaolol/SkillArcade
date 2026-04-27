package com.example.skillarcade.domain.model

data class Lesson(
    val id: String,
    val courseId: String,
    val title: String,
    val youtubeUrl: String,
    val durationMinutes: Int,
    val orderIndex: Int,
    val isCompleted: Boolean = false,
    val xpReward: Int = 10
)
